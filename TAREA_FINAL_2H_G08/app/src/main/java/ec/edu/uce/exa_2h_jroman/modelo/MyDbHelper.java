package ec.edu.uce.exa_2h_jroman.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.BaseColumns;
import android.support.v4.content.ContextCompat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import ec.edu.uce.exa_2h_jroman.R;

/**

 */

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BASE-VEHICULOS-SQL.db";
    private static final int DB_VERSION = 1;
    /*Variables para la creacion de tablas */
    private static final String STRING_CREATE_USER =
            "CREATE TABLE usuario (_id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT, clave TEXT);";
    private static final String STRING_CREATE_VEHICULO =
            "CREATE TABLE vehiculo (_id INTEGER PRIMARY KEY AUTOINCREMENT, placa TEXT, marca TEXT, fecFab DATE, costo REAL, matriculado INTEGER, color TEXT, foto TEXT, estado INTEGER, tipo TEXT);";
    private static final String STRING_CREATE_RESERVA =
            "CREATE TABLE reserva (_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, celular TEXT, fecRes DATE, fecEnt DATE, valor REAL, user INTEGER, placa TEXT, tipo TEXT);";

    Context contxt;
    /* Creacion de la carpeta donde se almacenan los datos*/
    public MyDbHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/DATOS_VEHICULOS/" +DB_NAME, null, DB_VERSION);
        contxt = context;
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
         /*
         * Convierte los Bitmap picture a un STring para el JSON
         * JSONified.
         * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos las tablas
        db.execSQL(STRING_CREATE_USER);
        db.execSQL(STRING_CREATE_VEHICULO);
        db.execSQL(STRING_CREATE_RESERVA);

        String insert;

        //VEHICULO
        Drawable drawable = ContextCompat.getDrawable(contxt, R.drawable.icon);

        //grabar nuevo registro a la base de datos
        // Valores
        ContentValues values = new ContentValues();
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.PLACA, "PBQ-1234");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.MARCA, "Ferrari");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.FECFAB, "2017-01-01");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.COSTO, 68570.0);
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.MATRICULADO, 1);
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.COLOR, "Otro");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.FOTO, getStringFromBitmap(((BitmapDrawable)drawable).getBitmap()));
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.ESTADO, 0);
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TIPO, "Automovil");

        db.insert(
                MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TABLE_NAME,
                null,
                values);
        //Vehiculo 2
        drawable = ContextCompat.getDrawable(contxt, R.drawable.auto2);
        values = new ContentValues();
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.PLACA, "ACD-2879");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.MARCA, "Honda");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.FECFAB, "2015-05-23");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.COSTO, 34690.0);
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.MATRICULADO, 0);
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.COLOR, "Blanco");
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.FOTO, getStringFromBitmap(((BitmapDrawable)drawable).getBitmap()));
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.ESTADO, 1);
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TIPO, "Camioneta");

        db.insert(
                MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TABLE_NAME,
                null,
                values);

        //USUARIO
        insert = "INSERT INTO "+EsquemaUsuario.ColumnasUsuario.TABLE_NAME+" ("+EsquemaUsuario.ColumnasUsuario.NAME+", "+EsquemaUsuario.ColumnasUsuario.PASS+") VALUES (\"Jimmy Roman\",\"jroman\");";
        db.execSQL(insert);
        insert = "INSERT INTO "+EsquemaUsuario.ColumnasUsuario.TABLE_NAME+" ("+EsquemaUsuario.ColumnasUsuario.NAME+", "+EsquemaUsuario.ColumnasUsuario.PASS+") VALUES (\"admin\",\"12345\");";
        db.execSQL(insert);

        //RESERVAS
        insert = "INSERT INTO "+EsquemaReserva.ColumnasReserva.TABLE_NAME+" ("+EsquemaReserva.ColumnasReserva.EMAIL+", "+EsquemaReserva.ColumnasReserva.CELULAR+", "+EsquemaReserva.ColumnasReserva.FECRES+", "+EsquemaReserva.ColumnasReserva.FECENT+", "+EsquemaReserva.ColumnasReserva.VALOR+", "+EsquemaReserva.ColumnasReserva.USER+", "+EsquemaReserva.ColumnasReserva.PLACA+") VALUES (\"j.roman12@hotmail.com\",\"0984518427\",\"2019-01-06\",\"2019-01-10\",60.0, 1,\"ACD-2879\");";
        db.execSQL(insert);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table usuario");
        db.execSQL("drop table vehiculo");
        db.execSQL("drop table reserva");
        onCreate(db);
    }

    public String getDbName() { return DB_NAME;}

    /**
     * Esquema de la tabla
     */
    public class EsquemaUsuario {

        public abstract class ColumnasUsuario implements BaseColumns {
            public static final String TABLE_NAME ="usuario";

            public static final String ID = "_id";
            public static final String NAME = "usuario";
            public static final String PASS = "clave";
        }
    }

    /**
     * Esquema de la tabla vehiculo
     */
    public class EsquemaVehiculo {

        public abstract class ColumnasVehiculo implements BaseColumns {
            public static final String TABLE_NAME ="vehiculo";

            public static final String ID = "_id";
            public static final String PLACA = "placa";
            public static final String MARCA = "marca";
            public static final String FECFAB = "fecFab";
            public static final String COSTO = "costo";
            public static final String MATRICULADO = "matriculado";
            public static final String COLOR = "color";
            public static final String FOTO = "foto";
            public static final String ESTADO = "estado";
            public static final String TIPO = "tipo";
        }
    }

    /**
     * Esquema de la tabla reserva
     */
    public class EsquemaReserva {

        public abstract class ColumnasReserva implements BaseColumns {
            public static final String TABLE_NAME ="reserva";

            public static final String ID = "_id";
            public static final String EMAIL = "email";
            public static final String CELULAR = "celular";
            public static final String FECRES = "fecRes";
            public static final String FECENT = "fecEnt";
            public static final String VALOR = "valor";
            public static final String USER = "user";
            public static final String PLACA = "placa";
            public static final String TIPOR = "tipor";
        }
    }
}