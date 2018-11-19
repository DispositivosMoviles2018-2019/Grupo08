package ec.edu.uce.controlador;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class EditarVehiculo_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static Vehiculo vehiculo;

    private EditText txtPlaca;
    private EditText txtMarca;
    private EditText txtCosto;
    private EditText txtColor;
    private Switch wsEnrollment;
    private TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarvehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (vehiculo != null) {
            fillData();
        }
    }

    public void edit(View view) {

        initFields();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Double cost = Double.parseDouble(txtCosto.getText().toString());
        Boolean isEnrollment = wsEnrollment.isChecked();
        Date date = new Date();
        try {
            date = sdf.parse(txtDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Vehiculo newVehiculo = new Vehiculo();
        newVehiculo.setPlaca(txtPlaca.getText().toString());
        newVehiculo.setMarca(txtMarca.getText().toString());
        newVehiculo.setCosto(cost);
        newVehiculo.setColor(txtColor.getText().toString());
        newVehiculo.setMatriculado(isEnrollment);
        newVehiculo.setFechaFabricacion(date);

        try {
            ListaVehiculos_Activity.lstVehiculos.remove(vehiculo);
            ListaVehiculos_Activity.lstVehiculos.add(newVehiculo);
            ListaVehiculos_Activity.adapter.notifyDataSetChanged();
            Toast.makeText(this, "El Vehiculo se edito correctamente", Toast.LENGTH_LONG).show();
            finish();
        } catch (CustomException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void initFields() {
        txtPlaca = findViewById(R.id.txt_license);
        txtMarca = findViewById(R.id.txt_brand);
        txtCosto = findViewById(R.id.txt_costo);
        txtColor = findViewById(R.id.txt_color);
        wsEnrollment = findViewById(R.id.sw_enrollment);
        txtDate = findViewById(R.id.txt_date);
    }

    public void fillData() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        initFields();
        txtPlaca.setText(vehiculo.getPlaca());
        txtMarca.setText(vehiculo.getMarca());
        txtCosto.setText(String.valueOf(vehiculo.getCosto()));
        txtColor.setText(vehiculo.getColor());
        wsEnrollment.setChecked(vehiculo.getMatriculado());
        txtDate.setText(sdf.format(vehiculo.getFechaFabricacion()));
    }

    public void showDataPicker(View view) {
        DialogFragment datePicker = new DateFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
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
}
