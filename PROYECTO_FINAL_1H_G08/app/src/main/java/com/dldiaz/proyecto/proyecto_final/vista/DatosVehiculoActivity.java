package com.dldiaz.proyecto.proyecto_final.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dldiaz.proyecto.proyecto_final.R;
import com.dldiaz.proyecto.proyecto_final.controlador.ListaAdapter;
import com.dldiaz.proyecto.proyecto_final.controlador.Util;
import com.dldiaz.proyecto.proyecto_final.modelo.Vehiculo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DatosVehiculoActivity extends AppCompatActivity {
    EditText placa, marca, costo, color;
    CalendarView fechaFabricacion;
    Switch matriculado;
    Button guardar, editar;
    TextView dateDisplay;
    ListView milistaM;
    int id = -1;
    private String string;
    private static Vehiculo vehiculo = new Vehiculo("ABC456", "Chevrolet", "14/05/2018", 2323, true, "rojo");
    List<Vehiculo> vehiculoList;
    ListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_vehiculo);
        placa = (EditText) findViewById(R.id.placa);
        marca = (EditText) findViewById(R.id.marca);
        costo = (EditText) findViewById(R.id.costo);
        color = (EditText) findViewById(R.id.color);
        fechaFabricacion = (CalendarView) findViewById(R.id.calendario);
        dateDisplay = (TextView) findViewById(R.id.displayFecha);
        matriculado = (Switch) findViewById(R.id.matriculado);
        guardar = (Button) findViewById(R.id.btnGuardar);
        editar = (Button) findViewById(R.id.btnLeer);
        milistaM = (ListView) findViewById(R.id.lista);

        fechaFabricacion.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);
                Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
            }
        });
        vehiculoList = new ArrayList<Vehiculo>();
        leerGson();
        milistaM.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("ingresando un click largo");
                final int posicion = i;
                id = posicion;
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DatosVehiculoActivity.this);
                dialogo1.setTitle("Alerta");
                dialogo1.setMessage("¿ Elimina este vehiculo con Placa:?" + vehiculoList.get(posicion).getPlaca());
                placa.setText(vehiculoList.get(posicion).getPlaca());
                marca.setText(vehiculoList.get(posicion).getMarca());
                color.setText(vehiculoList.get(posicion).getColor());
                costo.setText(Integer.toString(vehiculoList.get(posicion).getCosto()));
                dateDisplay.setText(vehiculoList.get(posicion).getFecFabricacion());
                matriculado.setText(Boolean.toString(vehiculoList.get(posicion).isMatriculado()));
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        vehiculoList.remove(posicion);
                        adapter.notifyDataSetChanged();
                        limpiar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String valor = costo.getText().toString();
                int costoFinal = Integer.parseInt(valor);
                String valorMatriculado = matriculado.getText().toString();
                Boolean matriculadoFinal = Boolean.parseBoolean(valorMatriculado);
                agregarVehiculos(placa.getText().toString(),
                        marca.getText().toString(),
                        dateDisplay.getText().toString(),
                        costoFinal,
                        matriculadoFinal,
                        color.getText().toString());
                limpiar();

            }
        });
        editar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String placaE = placa.getText().toString();
                String marcaE = marca.getText().toString();
                String valor = costo.getText().toString();
                int costoFinal = Integer.parseInt(valor);
                String colorE = color.getText().toString();
                String dateDisplayE = dateDisplay.getText().toString();
                String valorMatriculado = matriculado.getText().toString();
                Boolean matriculadoFinalE = Boolean.parseBoolean(valorMatriculado);
                Vehiculo vehiculo = new Vehiculo(placaE, marcaE, dateDisplayE, costoFinal, matriculadoFinalE, colorE);
                vehiculoList.set(id, vehiculo);
                id = -1;
                adapter.notifyDataSetChanged();
                limpiar();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemSalir) {
            GuardarGson();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void agregarVehiculos(String placa, String marca, String fecFabricacion, int costo, boolean matriculado, String color) {
        vehiculoList.add(new Vehiculo(placa, marca, fecFabricacion, costo, matriculado, color));
        adapter.notifyDataSetChanged();
    }
    public void limpiar(){
        placa.setText("");
        marca.setText("");
        dateDisplay.setText("");
        costo.setText("");
        matriculado.isChecked();
        color.setText("");
    }
    public void writeJson(View view) {
        Util.writeToFile(this, "vehiculo.txt", vehiculo.toJsonString());
    }

    public void dirs(View view) {
        String str = "Almacenamiento interno";
        File filesDir = getFilesDir();
        str = "getFileDir:" + filesDir.getAbsolutePath();
        dateDisplay.setText(str);
    }

    //Escribir la lista en formato Json
    public void GuardarGson() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(vehiculoList);
        System.out.println(">>>>>>>>Guardando Json" + jsonString);
        Util.writeToFile(this, "vehiculo.txt", jsonString);
    }

    //De Json a string
    public List<Vehiculo> leerGson() {
        Gson gson = new Gson();
        try {
            FileInputStream is = openFileInput("vehiculo.txt");
            System.out.println("archivo abierto");
            String result = Util.stringFromStream(is);
            // String r ="[{\"color\":\"azul\",\"costo\":2323,\"fecFabricacion\":\"15/01/2018\",\"marca\":\"Kia\",\"matriculado\":true,\"placa\":\"ABC123\"},{\"color\":\"rojo\",\"costo\":2323,\"fecFabricacion\":\"14/05/2018\",\"marca\":\"Chevrolet\",\"matriculado\":true,\"placa\":\"ABC456\"}]";
            Type vehiculoType = new TypeToken<List<Vehiculo>>() {
            }.getType();
            vehiculoList = gson.fromJson(result, vehiculoType);
            //  final ListaAdapter adapter = new ListaAdapter(this, vehiculoList);
            //ListView milistaM = (ListView) findViewById(R.id.lista);
            adapter = new ListaAdapter(this, vehiculoList);
            milistaM.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            for (Vehiculo vehiculo : vehiculoList) {
                System.out.println(vehiculo.getColor());
                System.out.println(vehiculo.getCosto());
                System.out.println(vehiculo.getFecFabricacion());
                System.out.println(vehiculo.getMarca());
                System.out.println(vehiculo.getPlaca());
                System.out.println(vehiculo.isMatriculado());
            }
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehiculoList;
    }
}
