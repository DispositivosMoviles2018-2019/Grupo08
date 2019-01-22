package com.dldiaz.proyecto.ddiazexamen2h.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dldiaz.proyecto.ddiazexamen2h.modelo.Reserva;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;
import com.dldiaz.proyecto.ddiazexamen2h.utilidades.ConexionSqliteHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehiculoImpl implements Crud {
    public static final String LOGTAG = "EMP_MNGMNT_SYS";
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;
    private static final  String[] columnas = {
            ConexionSqliteHelper.CAMPO_PLACA,
            ConexionSqliteHelper.CAMPO_MARCA,
            ConexionSqliteHelper.CAMPO_FECFABRICACION,
            ConexionSqliteHelper.CAMPO_COSTO,
            ConexionSqliteHelper.CAMPO_MATRICULADO,
            ConexionSqliteHelper.CAMPO_COLOR,
            ConexionSqliteHelper.CAMPO_ESTADO,
            ConexionSqliteHelper.CAMPO_TIPO
    };

    public VehiculoImpl(Context context) {
        dbhandler = new ConexionSqliteHelper(context);
    }


    public void open(){
        Log.i(LOGTAG,"Database Opened");
        db = dbhandler.getWritableDatabase();
    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    @Override
    public String crear(Object obj) {
        ContentValues values = new ContentValues();
        values.put(ConexionSqliteHelper.CAMPO_PLACA, ((Vehiculo) obj).getPlaca());
        values.put(ConexionSqliteHelper.CAMPO_MARCA, ((Vehiculo) obj).getMarca());
        values.put(ConexionSqliteHelper.CAMPO_FECFABRICACION,((Vehiculo) obj).getFecFabricacion());
        values.put(ConexionSqliteHelper.CAMPO_COSTO, ((Vehiculo) obj).getCosto());
        values.put(ConexionSqliteHelper.CAMPO_MATRICULADO, ((Vehiculo) obj).isMatriculado());
        values.put(ConexionSqliteHelper.CAMPO_COLOR, ((Vehiculo) obj).getColor());
        values.put(ConexionSqliteHelper.CAMPO_ESTADO, ((Vehiculo) obj).isEstado());
        values.put(ConexionSqliteHelper.CAMPO_TIPO, ((Vehiculo) obj).getTipo());
        db.insert(ConexionSqliteHelper.TABLA_VEHICULO,null,values);
        return "creado";
    }

    @Override
    public String actualizar(Object obj) {
       ContentValues values = new ContentValues();
        values.put(ConexionSqliteHelper.CAMPO_MARCA, ((Vehiculo) obj).getMarca());
        values.put(ConexionSqliteHelper.CAMPO_FECFABRICACION,((Vehiculo) obj).getFecFabricacion());
        values.put(ConexionSqliteHelper.CAMPO_COSTO, ((Vehiculo) obj).getCosto());
        values.put(ConexionSqliteHelper.CAMPO_MATRICULADO, ((Vehiculo) obj).isMatriculado());
        values.put(ConexionSqliteHelper.CAMPO_COLOR, ((Vehiculo) obj).getColor());
        values.put(ConexionSqliteHelper.CAMPO_ESTADO, ((Vehiculo) obj).isEstado());
        values.put(ConexionSqliteHelper.CAMPO_TIPO, ((Vehiculo) obj).getTipo());
        db.update(ConexionSqliteHelper.TABLA_VEHICULO,values,ConexionSqliteHelper.CAMPO_PLACA + "=?",new String[] {String.valueOf(((Vehiculo) obj).getPlaca())});
        return "actualizado";

    }

    @Override
    public String borrar(Object obj) {
        db.delete(ConexionSqliteHelper.TABLA_VEHICULO,ConexionSqliteHelper.CAMPO_PLACA + "='" + ((Vehiculo)obj).getPlaca()+"'",null);
        return "eliminado";
    }

    @Override
    public List<Vehiculo> listar() {
        Cursor cursor = db.query(ConexionSqliteHelper.TABLA_VEHICULO,columnas,null,null,null ,null ,null);
       List<Vehiculo> vehiculoList = new ArrayList<>();

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_PLACA)));
                vehiculo.setMarca(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_MARCA)));
                vehiculo.setFecFabricacion(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_FECFABRICACION)));
                vehiculo.setCosto(cursor.getInt(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_COSTO)));
                vehiculo.setMatriculado(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_ESTADO))));
                System.out.println("matriculaaaaaaaaaaaaaaaaaaaa" + Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_MATRICULADO))));
                vehiculo.setColor(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_COLOR)));
                vehiculo.setEstado(true);
                vehiculo.setTipo(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_TIPO)));
                vehiculoList.add(vehiculo);
            }
        }
        System.out.println("********************"+vehiculoList);
        return vehiculoList;
    }

    @Override
    public List<Reserva> listarR() {
        return null;
    }


    public Vehiculo getVehiculo(String placa) {
        Cursor cursor = db.query(ConexionSqliteHelper.TABLA_VEHICULO,columnas, ConexionSqliteHelper.CAMPO_PLACA + "=?",new String[] {String.valueOf(placa)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        Vehiculo vehiculo = new Vehiculo(cursor.getString(0),
                    cursor.getString(1),
                   cursor.getString(2) ,
                    cursor.getDouble(3),
                    (Boolean.parseBoolean(cursor.getString( 4))),
                    cursor.getString(5),
                    (Boolean.parseBoolean(cursor.getString( 6))),
                    cursor.getString(7));
        return vehiculo;
    }
}
