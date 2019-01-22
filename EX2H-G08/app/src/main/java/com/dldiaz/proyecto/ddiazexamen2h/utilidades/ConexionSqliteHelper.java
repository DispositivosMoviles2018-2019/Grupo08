package com.dldiaz.proyecto.ddiazexamen2h.utilidades;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

//Permite la conexion con la base de datos
public class ConexionSqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vehiculo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLA_USUARIO = "usuario";
    public static final String CAMPO_USUARIO = "usuario";
    public static final String CAMPO_PASSWORD = "password";

    public static final String TABLA_VEHICULO = "vehiculo";
    public static final String CAMPO_PLACA= "placa";
    public static final String CAMPO_MARCA = "marca";
    public static final String CAMPO_FECFABRICACION= "fecFabricacion";
    public static final String CAMPO_COSTO= "costo";
    public static final String CAMPO_MATRICULADO = "matriculado";
    public static final String CAMPO_COLOR = "color";
    public static final String CAMPO_IMAGEN = "imagen";
    public static final String CAMPO_ESTADO = "estado";
    public static final String CAMPO_TIPO = "tipo";

    public static final String TABLA_RESERVA = "reserva";
    public static final String CAMPO_ID= "id";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_CELULAR = "celular";
    public static final String CAMPO_FECHA_PRESTAMO = "fecha_prestamo";
    public static final String CAMPO_FECHA_ENTREGA = "fecha_entrega";
    public static final String CAMPO_VALOR_RESERVA = "valor_reserva";
    public static final String CAMPO_PLACA_RESERVA = "placa";


    public static final String CREAR_TABLA_USUARIO= "CREATE TABLE " + TABLA_USUARIO + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_USUARIO + " TEXT, "
            + CAMPO_PASSWORD + " TEXT) ";



    public static final String CREAR_TABLA_RESERVA = "CREATE TABLE " + TABLA_RESERVA + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_EMAIL + " TEXT, "
            + CAMPO_CELULAR + " TEXT, "
            + CAMPO_FECHA_PRESTAMO + " TEXT, "
            + CAMPO_FECHA_ENTREGA + " TEXT, "
            + CAMPO_VALOR_RESERVA + " REAL,  "
            + CAMPO_PLACA_RESERVA + " TEXT) ";

    public static final String CREAR_TABLA_VEHICULO = "CREATE TABLE " + TABLA_VEHICULO + "("
            + CAMPO_PLACA + " TEXT, "
            + CAMPO_MARCA + " TEXT, "
            + CAMPO_FECFABRICACION + " TEXT, "
            + CAMPO_COSTO + " REAL, "
            + CAMPO_MATRICULADO + " BOOL, "
            + CAMPO_COLOR + " TEXT, "
            + CAMPO_ESTADO + " BOOL, "
            + CAMPO_IMAGEN + "BLOB, "
            + CAMPO_TIPO + " TEXT )";
    //Creacioun de la BasedeDatos
    public ConexionSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ConexionSqliteHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Script de las tablas de la Base
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_RESERVA);
        db.execSQL(CREAR_TABLA_VEHICULO);
        db.execSQL(CREAR_TABLA_USUARIO);
    }

    //Verifica version antigua de BD  cada vez que se instala la app
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_RESERVA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_VEHICULO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIO);
        onCreate(db);
    }

}
