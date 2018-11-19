package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.vista.DateFragment;
import java.util.regex.*;

public class NuevoVehiculo_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Manejo_Vehiculos manejoVehiculos = new Manejo_Vehiculos();

    private EditText txtPlaca;
    private EditText txtMarca;
    private EditText txtCosto;
    private CheckBox chkRojo;
    private CheckBox chkPlomo;
    private CheckBox chkAzul;
    private CheckBox chkVerde;
    private Switch wsEnrollment;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevovehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void save(View view) {

        txtPlaca = findViewById(R.id.txt_license);

        txtMarca = findViewById(R.id.txt_brand);
        txtCosto = findViewById(R.id.txt_costo);

        chkRojo =findViewById(R.id.chk_Rojo);
        chkPlomo =findViewById(R.id.chk_Plomo);
        chkAzul =findViewById(R.id.chk_Azul);
        chkVerde =findViewById(R.id.chk_Verde);

        String colorEscogido="";
        boolean condicionColor=false;
        boolean condicionPlaca=false;


        if(chkAzul.isChecked()&&!(chkRojo.isChecked())&&!(chkPlomo.isChecked())&&!(chkVerde.isChecked())){
              colorEscogido="Azul";
            condicionColor=true;
            }else if(chkVerde.isChecked()&&!(chkRojo.isChecked())&&!(chkPlomo.isChecked())&&!(chkAzul.isChecked())){
                colorEscogido="Verde";
            condicionColor=true;
            }else if (chkPlomo.isChecked()&&!(chkRojo.isChecked())&&!(chkAzul.isChecked())&&!(chkVerde.isChecked())){
                colorEscogido="Plomo";
            condicionColor=true;
            }else if(chkRojo.isChecked()&&!(chkAzul.isChecked())&&!(chkPlomo.isChecked())&&!(chkVerde.isChecked())){
                colorEscogido="Rojo";
            condicionColor=true;
            }
            else{
                colorEscogido="Otro color";
                Toast.makeText(this, "Debe escoger un solo color de los que se muestran", Toast.LENGTH_LONG).show();
            condicionColor=false;
            }

        //txtColor = findViewById(R.id.txt_color);
        wsEnrollment = findViewById(R.id.sw_enrollment);
        txtDate = findViewById(R.id.txt_date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        Double cost = Double.parseDouble(txtCosto.getText().toString());
        Boolean isEnrollment = wsEnrollment.isChecked();
        Date date = new Date();
        try {
            date = sdf.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Vehiculo vehiculo = new Vehiculo();

        /*PREGUNTA1*/
        String placa=txtPlaca.getText().toString();

        Pattern patron = Pattern.compile("[A-Z]{3}-[0-9]{3}[02468]{1}");
        Matcher mat = patron.matcher(placa);

        if (mat.matches()) {
           condicionPlaca=true;

        } else {
           Toast.makeText(this, "El patron para la placa es XXX-NNNA :A es par", Toast.LENGTH_LONG).show();
           condicionPlaca=false;
        }


        if(condicionPlaca==true && condicionColor ==true){

        vehiculo.setPlaca(txtPlaca.getText().toString());
        vehiculo.setMarca(txtMarca.getText().toString());
        vehiculo.setCosto(cost);
        vehiculo.setColor(colorEscogido);
        vehiculo.setMatriculado(isEnrollment);
        vehiculo.setFechaFabricacion(date);


        try {
            if (!ListaVehiculos_Activity.lstVehiculos.contains(vehiculo)) {
                ListaVehiculos_Activity.lstVehiculos.add(vehiculo);
                ListaVehiculos_Activity.adapter.notifyDataSetChanged();

                Toast.makeText(this, "Vehiculo agregado correctamente", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "El Vehiculo con la placa " + vehiculo.getPlaca().toUpperCase() + " ya existe", Toast.LENGTH_LONG).show();
            }
        } catch (CustomException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        }else{
            Toast.makeText(this, "Los campos no estan correctos por favor verifique", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        String date = sdf.format(c.getTime());
        TextView txtFecha = findViewById(R.id.txt_date);
        txtFecha.setText(date);
    }


    public void showDataPicker(View view) {
        DialogFragment datePicker = new DateFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }
}
