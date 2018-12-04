package ec.edu.uce.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.vista.DataAdapter;
import ec.edu.uce.vista.ItemClickListener;

public class ListaVehiculos_Activity extends AppCompatActivity {

    private Manejo_Vehiculos manejoVehiculos = new Manejo_Vehiculos();

    public static List<Vehiculo> lstVehiculos = new ArrayList<>();
    public static DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listavehiculos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa los lstVehiculos
        initVehiculos();

        // Inicializa el RecyclerView
        RecyclerView recyclerStdudents = findViewById(R.id.RecyclerID);
        recyclerStdudents.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DataAdapter(lstVehiculos, this);
        recyclerStdudents.setAdapter(adapter);

        // Define el ClickListener
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListaVehiculos_Activity.this);
                    builder.setTitle("Seleccione una opción");

                    String[] options = {"Editar", "Eliminar"};
                    builder.setItems(options, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0: // Editar
                                    EditarVehiculo_Activity.vehiculo = lstVehiculos.get(position);

                                    Intent intent = new Intent(ListaVehiculos_Activity.this, EditarVehiculo_Activity.class);
                                    startActivity(intent);
                                    break;
                                case 1: // Eliminar
                                    lstVehiculos.remove(position);
                                    System.out.println("Size: " + lstVehiculos.size());
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(ListaVehiculos_Activity.this, "Manten presionado para ver las opciones", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Carga los datos iniciales
    public void initVehiculos() {
        if (manejoVehiculos.existFile()) {
            lstVehiculos = manejoVehiculos.getVehiculos();
        } else {
            lstVehiculos.add(new Vehiculo("XTR-9784", "Audi", new GregorianCalendar(2015, 11, 13).getTime(), 79990.0, true, "Negro"));
            lstVehiculos.add(new Vehiculo("CCD-0789", "Honda", new GregorianCalendar(1998, 3, 5).getTime(), 15340.0, false, "Blanco"));
        }
    }

    //Cuando sale de la aplicacion
    public void onStop () {
        super.onStop();
        guardarData(lstVehiculos);
    }

    //Persistencia de los datos
    public void guardarData(List<Vehiculo> lstVehiculos) {
        try {
            if (!manejoVehiculos.existFile()) {
                manejoVehiculos.initResources(this);
            }
            this.lstVehiculos = manejoVehiculos.save(lstVehiculos);
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_LONG).show();
        } catch (CustomException e) {

        }
    }

    //Llena el menu superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Cuando se presiona en las opciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //Opcion : persistir
        if (id == R.id.action_persist) {
            guardarData(lstVehiculos);
            return true;
            //Opcion : Salir
        } else if (id == R.id.action_close) {
            guardarData(lstVehiculos);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Redicciona al activityForm
    public void redirectFormActivity(View view) {
        Intent intent = new Intent(this, NuevoVehiculo_Activity.class);
        startActivity(intent);
    }

}
