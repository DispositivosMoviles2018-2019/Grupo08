package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;
import com.dldiaz.proyecto.ddiazexamen2h.utilidades.ConexionSqliteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class VehiculoCrearFragment extends Fragment  {
    private static final int CAMARA_CODE = 1;
    ImageView imageAuto;
    Vehiculo vehiculo;
    EditText placa, marca, costo;
    TextView dateDisplay;
    CalendarView fecFabricacion;
    Switch matriculado, estado;
    RadioButton color1, color2, color3, tipo1, tipo2, tipo3;
    RadioGroup groupColor;
    String color, tipo;
    VehiculoImpl vehiculoData;
    Button insertar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vehiculo_crear, container, false);
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
        tipo1 = (RadioButton) v.findViewById(R.id.camioneta);
        tipo2 = (RadioButton) v.findViewById(R.id.automovil);
        tipo3 = (RadioButton) v.findViewById(R.id.furgoneta);
        imageAuto = (ImageView) v.findViewById(R.id.imageAuto);

        guardarFecha();
//        imageAuto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirCamara();
//            }
//        });

        insertar = (Button) v.findViewById(R.id.btnInsertarVehiculo);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarVehiculo();
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

    public void insertarVehiculo(){
        vehiculo = new Vehiculo();
        vehiculo.setPlaca(placa.getText().toString());
        vehiculo.setMarca(marca.getText().toString());
        vehiculo.setFecFabricacion(dateDisplay.getText().toString());
        vehiculo.setCosto(Double.parseDouble(costo.getText().toString()));
        vehiculo.setMatriculado( matriculado.isChecked());
        vehiculo.setColor(setColor());
        System.out.println("COLOR"+ setColor());
        vehiculo.setEstado(estado.isChecked());
        vehiculo.setTipo(setTipo());
        vehiculoData.crear(vehiculo);
        Toast t = Toast.makeText(this.getContext(), "Vehiculo " + vehiculo.getPlaca() + "ha sido creado correctamente!", Toast.LENGTH_SHORT);
        t.show();
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