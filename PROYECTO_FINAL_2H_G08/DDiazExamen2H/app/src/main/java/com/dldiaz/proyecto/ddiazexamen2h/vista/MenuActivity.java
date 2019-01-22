package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;

import java.io.*;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private VehiculoImpl vehiculoImpl;
    List<Vehiculo> vehiculos;
    private FileOutputStream fileOut;
    private ObjectOutputStream objectOut;
    private String filePath;
    private Context parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_general);
    }
    public void menuVehiculo(View view){
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.btnVehiculos:
                miIntent = new Intent(MenuActivity.this,VehiculoActivity.class);
                break;
        }
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }
    public void menuReserva(View view){
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.btnReservas:
                miIntent = new Intent(MenuActivity.this,ReservaActivity.class);
                break;
        }
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }

    public void exportDB(View view) {
        String fileName = "RespaldoVehiculos.json";
        String fileName1 = "RespaldoReservas.json";
        try {
            filePath = "/storage/emulated/0/Documentos/" + fileName;
            //Crea el archivo en la direccion ingresada con el nombre del archivo
            fileOut = new FileOutputStream(filePath);
            //Instancia para poder escribir datos dentro del archivo
            objectOut = new ObjectOutputStream(fileOut);


            vehiculoImpl = new VehiculoImpl(this);
            vehiculoImpl.open();
            vehiculos = vehiculoImpl.listar();
            objectOut.writeObject(vehiculos.toString());
            //Permite sobreescribir el archivo
            fileOut.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//        try (Writer writer = new FileWriter("Output.json")) {
//            Gson gson = new GsonBuilder().create();
//            gson.toJson(vehiculos, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
