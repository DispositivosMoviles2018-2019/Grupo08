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
import java.util.List;

public class ReservaImpl  implements Crud{
    public static final String LOGTAG = "EMP_MNGMNT_SYS";
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;
    private static final  String[] columnas = {
            ConexionSqliteHelper.CAMPO_ID,
            ConexionSqliteHelper.CAMPO_EMAIL,
            ConexionSqliteHelper.CAMPO_CELULAR,
            ConexionSqliteHelper.CAMPO_FECHA_PRESTAMO,
            ConexionSqliteHelper.CAMPO_FECHA_ENTREGA,
            ConexionSqliteHelper.CAMPO_VALOR_RESERVA,
            ConexionSqliteHelper.CAMPO_PLACA
    };

    public ReservaImpl(Context context) {
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
        ContentValues valuesR = new ContentValues();
        valuesR.put(ConexionSqliteHelper.CAMPO_EMAIL,((Reserva) obj).getEmail());
        valuesR.put(ConexionSqliteHelper.CAMPO_CELULAR, ((Reserva) obj).getCelular());
        valuesR.put(ConexionSqliteHelper.CAMPO_FECHA_PRESTAMO, ((Reserva) obj).getFecha_pestramo());
        valuesR.put(ConexionSqliteHelper.CAMPO_FECHA_ENTREGA, ((Reserva) obj).getFecha_entrega());
        valuesR.put(ConexionSqliteHelper.CAMPO_VALOR_RESERVA, ((Reserva) obj).getValor_reserva());
        valuesR.put(ConexionSqliteHelper.CAMPO_PLACA_RESERVA, ((Reserva) obj).getPlaca());
        db.insert(ConexionSqliteHelper.TABLA_RESERVA,null,valuesR);
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
        return null;
    }

    @Override
    public List<Reserva> listarR() {
        Cursor cursor = db.query(ConexionSqliteHelper.TABLA_RESERVA,columnas,null,null,null ,null ,null);
        List<Reserva> reservaList = new ArrayList<>();
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Reserva reserva = new Reserva();
                reserva.setId(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_ID)));
                reserva.setEmail(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_EMAIL)));
                reserva.setCelular(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_CELULAR)));
                reserva.setFecha_pestramo(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_FECHA_PRESTAMO)));
                reserva.setFecha_entrega(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_FECHA_ENTREGA)));
                reserva.setValor_reserva(cursor.getInt(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_VALOR_RESERVA)));
                reserva.setPlaca(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_PLACA)));
             //   reserva.setP(cursor.getString(cursor.getColumnIndex(ConexionSqliteHelper.CAMPO_CELULAR)));
                reservaList.add(reserva);
            }
        }
        System.out.println("********************"+reservaList);
        return reservaList;
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
