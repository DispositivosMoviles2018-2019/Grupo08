package ec.edu.uce.dvallejo_ex_1h.vista;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.edu.uce.dvallejo_ex_1h.R;
import ec.edu.uce.dvallejo_ex_1h.controlador.VehiculoBean;

public class Automovil extends AppCompatActivity {

    private ArrayList<VehiculoBean> listaB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automovil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TextView txt = (TextView)findViewById(R.id.lbl_titulo);
        final boolean rRojo, rPlomo, rAzul, rVerde;

        rRojo = (CheckBox)findViewById(R.id.rd_Rojo);
        rPlomo = (RadioButton)findViewById(R.id.rd_Plomo);
        rAzul = (RadioButton)findViewById(R.id.rd_Azul);
        rVerde = (RadioButton)findViewById(R.id.rd_Verde);


        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.rg_colores);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()            {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int selectedId) {
                //Log.e("RADIO",""+selectedId);
                if(rBlanco.isChecked() || rNegro.isChecked()) {
                    TextView txt = (TextView)findViewById(R.id.txt_otro);
                    txt.setFocusable(false);
                    //Log.e("RADIO","b-n");
                }
                else {
                    TextView txt = (TextView) findViewById(R.id.txt_otro);
                    txt.setFocusableInTouchMode(true);
                    txt.setFocusable(true);
                }
            }
        });

        int tipo = (int) getIntent().getExtras().get("tipo");

        //INSERTAR
        if(tipo==1) {
            txt.setText("INSERCION DE UN NUEVO AUTOMOVIL");
        }
        //EDITAR
        else {
            VehiculoBean auto = new VehiculoBean();

            String selec = (String) getIntent().getExtras().get("placa"); //obtengo la placa

            listaB = (ArrayList<VehiculoBean>)getIntent().getExtras().get("listaBean");

            for (int i=0; i<listaB.size(); i++) {
                if(listaB.get(i).getPlaca().equals(selec)) {
                    auto = listaB.get(i);
                    break;
                }
            }

            txt.setText("EDICION DE UN AUTOMOVIL");

            EditText edit = (EditText)findViewById(R.id.txt_placa);
            edit.setText(auto.getPlaca());
            edit.setFocusable(false);

            edit = (EditText)findViewById(R.id.txt_marca);
            edit.setText(auto.getMarca());
            edit.setFocusable(false);

            edit = (EditText)findViewById(R.id.txt_fecha);
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
            edit.setText(formateador.format(auto.getFechaFab()));
            edit.setFocusable(false);

            edit = (EditText)findViewById(R.id.txt_costo);
            edit.setText( String.valueOf(auto.getCosto()) );

            edit = (EditText)findViewById(R.id.txt_matriculado);
            if(auto.getMatriculado())
                edit.setText( "si" );
            else
                edit.setText( "no" );

            if(auto.getColor().equalsIgnoreCase("blanco")) {
                rBlanco.setChecked(true);
            } else if(auto.getColor().equalsIgnoreCase("negro")) {
                rNegro.setChecked(true);
            }
            else {
                rOtro.setChecked(true);
                edit = (EditText)findViewById(R.id.txt_otro);
                edit.setText(auto.getColor());
            }
        }
    }

    public void regresar(View view) {
        finish();
    }


    //FUNCION QUE SE EJECUTA AL DAR CLIC EN EL BOTON OK
    public void ok(View view) {
        VehiculoBean auto = new VehiculoBean();

        EditText edit = (EditText)findViewById(R.id.txt_placa);

        //validamos la placa
        Pattern pat = Pattern.compile("[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]");
        //Pattern pat = Pattern.compile("[A-Z][a-z][a-zA-Z]-[0-9][0-9]");
        Matcher mat = pat.matcher(edit.getText().toString());
        if(mat.matches()) {
            auto.setPlaca(edit.getText().toString());
        }
        else {
            Toast.makeText(getApplicationContext(), "Error, formato de placa incorrecto!" , Toast.LENGTH_LONG).show();
            return;
        }

        edit = (EditText)findViewById(R.id.txt_marca);
        auto.setMarca(edit.getText().toString());

        edit = (EditText)findViewById(R.id.txt_fecha);
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd"); //"dd/MM/yy" "dd-MM-yy"
        Date date = null;

        Pattern pat2 = Pattern.compile("[0-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]");
        Matcher mat2 = pat2.matcher(edit.getText().toString());
        if(mat2.matches()) {
            try {
                date = formateador.parse(edit.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error, fecha no valida!" , Toast.LENGTH_LONG)
                        .show();
                date = new Date("1990/01/01");
            }
            auto.setFechaFab(date);
        }
        else {
            Toast.makeText(getApplicationContext(), "Error, formato de fecha incorrecto!" , Toast.LENGTH_LONG).show();
            return;
        }


        edit = (EditText)findViewById(R.id.txt_costo);
        auto.setCosto(Double.parseDouble(edit.getText().toString()));

        edit = (EditText)findViewById(R.id.txt_matriculado);
        if(edit.getText().toString().equalsIgnoreCase("si"))
            auto.setMatriculado(true);
        else
            auto.setMatriculado(false);

        //COLOR
        RadioButton rBlanco, rNegro, rOtro;

        rBlanco = (RadioButton)findViewById(R.id.rd_blanco);
        rNegro = (RadioButton)findViewById(R.id.rd_negro);
        rOtro = (RadioButton)findViewById(R.id.rd_otro);

        if(rBlanco.isChecked())
            auto.setColor("Blanco");
        if(rNegro.isChecked())
            auto.setColor("Negro");

        if(rOtro.isChecked()) {
            edit = (EditText)findViewById(R.id.txt_otro);
            auto.setColor(edit.getText().toString());
        }

        /*lista = (ArrayList<VehiculoBean>)getIntent().getSerializableExtra("lista");
        //arch = (ManejoArchivo) getIntent().getSerializableExtra("archivo");
        arch.setListadoBean(lista);
        arch.guardaArchivo(auto, "prueba.json");*/

        int tipo = (int) getIntent().getExtras().get("tipo");

        //INSERCION
        if(tipo==1) {
            listaB.add(auto);
        }
        //EDICION
        else {
            listaB = (ArrayList<VehiculoBean>)getIntent().getExtras().get("listaBean");
            //if(listaB==null) Log.e("EDI","nulo");
            String placa = (String) getIntent().getExtras().get("placa");

            for (int i=0; i<listaB.size(); i++) {
                if(listaB.get(i).getPlaca().equals(placa)) {
                    listaB.remove(i);
                    break;
                }
            }
            listaB.add(auto);
        }

        guardaArchivo("autos.json");

        finish();
    }

    public void guardaArchivo(String nombre_archivo) {
        Gson gson = new Gson();
        String jsonString;
        VehiculoBean auto = new VehiculoBean();

        OutputStreamWriter fout=null;

        Log.e("ESC", getFilesDir().getPath());

        try {
            fout = new OutputStreamWriter(getApplicationContext().openFileOutput(nombre_archivo, Activity.MODE_PRIVATE));
            fout.write("{\"TotalV\":"+listaB.size()+",\n\r");

            for(int i=0; i<listaB.size(); i++) {
                jsonString = "\"Vehiculo "+(i+1)+": ";

                auto = listaB.get(i);

                jsonString += gson.toJson(auto);

                Log.e("FILE", jsonString.toString());

                if(i<listaB.size()-1)
                    jsonString += ",\n\r";
                fout.write(jsonString);
                Log.e("JSON: "+(i+1), jsonString);
            }
            fout.write("}\n\r");
            fout.close();
        } catch (Exception e) {
            Toast mensaje = Toast.makeText(getApplicationContext(), "Error grabando archivo "+e.toString(), Toast.LENGTH_LONG);
            mensaje.show();
        }
    }
}

