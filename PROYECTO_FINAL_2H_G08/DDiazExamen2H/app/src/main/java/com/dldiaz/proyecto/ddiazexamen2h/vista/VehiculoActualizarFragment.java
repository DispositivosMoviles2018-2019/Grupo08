package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;
import com.dldiaz.proyecto.ddiazexamen2h.utilidades.ConexionSqliteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VehiculoActualizarFragment extends Fragment {

    private static final int CAMARA_CODE = 1;
    ImageView imageAuto;
    Vehiculo vehiculo;
    Vehiculo vehiculoExistente;
    EditText placa, marca, costo;
    TextView dateDisplay;
    CalendarView fecFabricacion;
    Switch matriculado, estado;
    RadioButton color1, color2, color3, tipo1, tipo2, tipo3;
    RadioGroup groupColor,groupTipo;
    String color, tipo;
    ListView miLista;
    VehiculoImpl vehiculoData;
    Button actualizar,buscar;
    List<Vehiculo> listaVehiculos;
    ConexionSqliteHelper conexion;
    String placaId;
    String mode;
    private static final String EXTRA_PLACA = "com.dldiaz.proyecto.ddiazexamen2h.placaId";
    private static final String EXTRA_UPDATE = "com.dldiaz.proyecto.ddiazexamen2h.add";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vehiculo_actualizar, container, false);
        placa = (EditText) v.findViewById(R.id.placa);
        marca = (EditText) v.findViewById(R.id.marca);
        fecFabricacion = (CalendarView) v.findViewById(R.id.fecFabricacion);
        dateDisplay = (TextView) v.findViewById(R.id.displayFecha);
        costo = (EditText) v.findViewById(R.id.costo);
        matriculado = (Switch) v.findViewById(R.id.matriculado);
        groupColor = (RadioGroup) v.findViewById(R.id.color);
        color1 = (RadioButton) v.findViewById(R.id.blanco);
        color2 = (RadioButton) v.findViewById(R.id.negro);
        color3 = (RadioButton) v.findViewById(R.id.otro);
        estado = (Switch) v.findViewById(R.id.estado);
        groupColor = (RadioGroup) v.findViewById(R.id.tipo);
        tipo1 = (RadioButton) v.findViewById(R.id.camioneta);
        tipo2 = (RadioButton) v.findViewById(R.id.automovil);
        tipo3 = (RadioButton) v.findViewById(R.id.furgoneta);
        imageAuto = (ImageView) v.findViewById(R.id.imageAuto);
        buscar=(Button) v.findViewById(R.id.btnBuscarPlaca);
        guardarFecha();

//        imageAuto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirCamara();
//            }
//        });
        groupColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.blanco){
                    vehiculo.setColor("Blanco");
                }
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("*****************************************"+placa.getText().toString());
                inicializarVehiculo(placa.getText().toString());
            }
        });

        actualizar = (Button) v.findViewById(R.id.btnActualizarVehiculo);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarVehiculo();
                //System.out.println("**************"+ placaId.getText().toString());
                //inicializarVehiculo(placaId.getText().toString());
            }
        });
        vehiculoData = new VehiculoImpl(this.getContext());
        vehiculoData.open();

        return v;
    }

        public void guardarFecha(){
        fecFabricacion.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                dateDisplay.setText(i2 + " / " + i1 + 1 + " / " + i);
            }
        });
    }


    public void actualizarVehiculo(){
        vehiculoExistente.setPlaca(placa.getText().toString());
        vehiculoExistente.setMarca(marca.getText().toString());
        vehiculoExistente.setFecFabricacion(dateDisplay.getText().toString());
        vehiculoExistente.setCosto(Double.parseDouble(costo.getText().toString()));
        vehiculoExistente.setMatriculado( matriculado.isChecked());
        vehiculoExistente.setColor(setColor());
        vehiculoExistente.setEstado(estado.isChecked());
        System.out.println("ESTADOOOOOOOOOOOO" + estado.isChecked());
        vehiculoExistente.setTipo(setTipo());
        vehiculoData.actualizar(vehiculoExistente);
        Toast t = Toast.makeText(this.getContext(), "Vehiculo " + "ha sido actualizado correctamente!", Toast.LENGTH_SHORT);
        t.show();
        //  Intent i = new Intent(VehiculoCrearFragment.this,VehiculoActivity.class);
        //startActivity(i);

    }
    private void inicializarVehiculo(String placaV){
        vehiculoExistente = vehiculoData.getVehiculo(placaV);
        placa.setText(vehiculoExistente.getPlaca());
        marca.setText(vehiculoExistente.getMarca());
        dateDisplay.setText(vehiculoExistente.getFecFabricacion());
        costo.setText(Double.toString(vehiculoExistente.getCosto()));
        matriculado.setText(Boolean.toString(vehiculoExistente.isMatriculado()));
       // groupColor.check(vehiculoExistente.getColor().equals("blanco") ? R.id.blanco : R.id.negro );
        estado.setText(Boolean.toString(vehiculoExistente.isEstado()));
       // groupTipo.check(vehiculoExistente.getTipo().equals("camioneta") ? R.id.camioneta : R.id.furgoneta );


    }
    public String setColor() {
        if(color1.isChecked()){
            color = "blanco";
        }else if(color2.isChecked()){
            color = "negro";
        }else {
            color ="otro";
        }
        return color;
    }
    public String setTipo() {
        if(tipo1.isChecked()){
            tipo = "camioneta";
        }else if(tipo2.isChecked()){
            tipo = "automovil";
        }else {
            tipo ="furgoneta";
        }
        return tipo;
    }

    private void abrirCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_CODE);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAMARA_CODE) {
//            Bundle extrastras = data.getExtras();
//            Bitmap bitmap = (Bitmap) extrastras.get("data");
//            imageAuto.setImageBitmap(bitmap);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            byte[] buffer = out.toByteArray();
//            vehiculo.setFoto(buffer);
//        }
//    }
}
