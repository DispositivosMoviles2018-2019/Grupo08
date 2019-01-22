package ec.edu.uce.exa_2h_jroman.vista;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.edu.uce.exa_2h_jroman.R;
import ec.edu.uce.exa_2h_jroman.controlador.ReservaControl;
import ec.edu.uce.exa_2h_jroman.modelo.Globales;
import ec.edu.uce.exa_2h_jroman.modelo.InterfazCRUD;
import ec.edu.uce.exa_2h_jroman.modelo.MyDbHelper;
import ec.edu.uce.exa_2h_jroman.modelo.Reserva;
import ec.edu.uce.exa_2h_jroman.modelo.Usuario;


public class ReservarActivity extends AppCompatActivity implements InterfazCRUD {

    private final double VALOR_DIA = 60.0;


    MyDbHelper mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;

    ArrayList<Reserva> listaReserva;

    Spinner placas;
    TextView numeroR, valor;
    EditText mail, fechaP, fechaE, celu;

    Button btn_ok, btn_cancel;

    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        listaReserva = new ArrayList<Reserva>();

        placas = (Spinner)findViewById(R.id.spinPlacas);
        numeroR = (TextView)findViewById(R.id.txtReserva);
        mail = (EditText)findViewById(R.id.txtMail);
        celu = (EditText)findViewById(R.id.txtCelular);
        fechaP = (EditText)findViewById(R.id.txtFechaP);
        fechaE = (EditText)findViewById(R.id.txtFechaE);
        valor = (TextView)findViewById(R.id.txtValor);

        btn_ok = (Button)findViewById(R.id.btnOk);
        btn_cancel = (Button)findViewById(R.id.btnCancel);

        mHelper = new MyDbHelper(this);
        mDb = mHelper.getReadableDatabase();

        /*Cursor c = db.query(
                LawyerEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );*/
        //String[] columns = new String[] {MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.ID, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.PLACA, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.MARCA, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.FECFAB, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.COSTO, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.MATRICULADO, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.COLOR, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.FOTO, MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.ESTADO};
        //mCursor = mDb.query(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TABLE_NAME, columns, null, null, null, null, null);

        //cargar las reservas de la base a la 'listaReservas'
        String query = "SELECT _id, email, celular, fecRes, fecEnt, valor, user, placa FROM "+MyDbHelper.EsquemaReserva.ColumnasReserva.TABLE_NAME;
        Log.e("QUERY", query);


        mCursor = mDb.rawQuery(query, null);
        Reserva reserva;

        Date fecReserva=new Date();
        Date fecEntrega=new Date();
        if(mCursor.getCount()>0) {
            for (int i = 0; i < mCursor.getCount(); i++) {
                mCursor.moveToPosition(i);
                Integer numeroReserva = mCursor.getInt(0);
                String email = mCursor.getString(1);
                String celular = mCursor.getString(2);
                try {
                    fecReserva = simpleDate.parse(mCursor.getString(3));
                    fecEntrega = simpleDate.parse(mCursor.getString(4));
                } catch (Exception e) {
                    Log.e("error", "leyendo fecha");
                }

                Double valor = mCursor.getDouble(5);
                int user = mCursor.getInt(6);
                String placa = mCursor.getString(7);

                reserva = new Reserva(numeroReserva, email, celular, fecReserva, fecEntrega, valor, user, placa);
                listaReserva.add(reserva);
            }
        }

        //seleccionamos las placas de los autos no reservados, osea 'estado' igual a 0
        query = "SELECT "+MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.PLACA+" FROM "+MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TABLE_NAME+" WHERE "+MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.ESTADO+"=?";
        Log.e("QUERY", query);

        mCursor = mDb.rawQuery(query, new String[]{"0"});

        if(mCursor.getCount()<=0) {
            mDb.close();
            mCursor.close();
            Toast.makeText(getApplicationContext(), "NO EXISTEN AUTOS DISPONIBLES EN ESTE MOMENTO!", Toast.LENGTH_LONG).show();
            return;
        }
        //llenar las placas
        ArrayList<String> lista = new ArrayList<String>();
        for (int i=0; i<mCursor.getCount(); i++) {
            mCursor.moveToPosition(i);
            lista.add(mCursor.getString(0)); //PLACA
        }
        placas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista));
        mDb.close();
        mCursor.close();
    }

    public void respaldo(View view) {
        ReservaControl reserva = new ReservaControl();
        String name = reserva.guardarJson(listaReserva);
        Toast.makeText(getApplicationContext(), "Ok, respaldo\n"+name, Toast.LENGTH_LONG).show();
    }



    @Override
    public String crear(Object obj) {
        listaReserva.add((Reserva)obj);
        return "ok";
    }

    @Override
    public String actualizar(Object id) {
        return null;
    }

    @Override
    public String borrar(Object id) {
        return null;
    }

    @Override
    public Object buscarPorParametro(Collection lista, Object parametro) {
        return null;
    }

    @Override
    public Collection listar() {
        return null;
    }

    public void botonOK(View view) {

        //VALIDACIONES

        if(placas.getCount() <=0) {
            Toast.makeText(getApplicationContext(), "NO EXISTEN AUTOS DISPONIBLES EN ESTE MOMENTO!", Toast.LENGTH_LONG).show();
            return;
        }

        Pattern restriccioncorreo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher validarcorreo = restriccioncorreo.matcher(mail.getText());
        if (!validarcorreo.matches() || mail.getText().equals("")) {
            Toast.makeText(getBaseContext(), "Direccion de correo no valida" + "\n", Toast.LENGTH_LONG).show();
            return;
        }

        Pattern restriccioncelu = Pattern.compile("\\d{10}");
        Matcher validarcelu = restriccioncelu.matcher(celu.getText());
        if (!validarcelu.matches() || fechaP.getText().equals("")) {
            Toast.makeText(getBaseContext(), "Numero de celular no valido" + "\n", Toast.LENGTH_LONG).show();
            return;
        }

        Pattern restriccionfecha = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
        Matcher validarfecha = restriccionfecha.matcher(fechaP.getText());
        if (!validarfecha.matches() || fechaP.getText().equals("")) {
            Toast.makeText(getBaseContext(), "Fecha de Prestamo no valida" + "\n", Toast.LENGTH_LONG).show();
            return;
        }

        validarfecha = restriccionfecha.matcher(fechaE.getText());
        if (!validarfecha.matches() || fechaP.getText().equals("")) {
            Toast.makeText(getBaseContext(), "Fecha de Entrega no valida" + "\n", Toast.LENGTH_LONG).show();
            return;
        }

        int numUltimo = 0;
        double val = 0;

        mDb = mHelper.getReadableDatabase();

        String columns[] = new String[]{MyDbHelper.EsquemaReserva.ColumnasReserva.ID};

        Cursor c = mDb.query(
                MyDbHelper.EsquemaReserva.ColumnasReserva.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if(c!=null && c.getCount()>0) {
            c.moveToLast();
            numUltimo = c.getInt(0);
            mDb.close();
            c.close();
        }

        try {
            val = restarFechas(simpleDate.parse(fechaE.getText().toString()), simpleDate.parse(fechaP.getText().toString())) * VALOR_DIA;
            valor.setText("Valor a pagar: $"+val);
        } catch (Exception e) {
            System.out.println("Error de fechas "+e);
        }

        Date fP=null, fE=null;

        try {
            fP = simpleDate.parse(fechaP.getText().toString());
            fE = simpleDate.parse(fechaE.getText().toString());
        } catch (Exception e) {
            System.out.println("Error formato fechas "+e);
        }

        Globales g = Globales.getInstance();
        Usuario user = g.getUser();

        Reserva reserva = new Reserva(numUltimo, mail.getText().toString(), celu.getText().toString(), fP, fE, val, user.getId(), placas.getSelectedItem().toString());

        crear(reserva);

        //actualizar vehiculo el estado del vehiculo a reservado
        // Valores
        ContentValues values = new ContentValues();

        // Valores nuevos del nombre y teléfono
        values.put(MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.ESTADO, 1);

        // WHERE
        String selection = MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.PLACA + " = ?";
        String[] selectionArgs = {placas.getSelectedItem().toString()};

        // Actualizar
        mDb = mHelper.getWritableDatabase();
        mDb.update(
                MyDbHelper.EsquemaVehiculo.ColumnasVehiculo.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        mHelper.close();
        Toast.makeText(getApplicationContext(),"Reservacion creada\n"+"Auto: "+placas.getSelectedItem().toString()+" Total: "+val, Toast.LENGTH_LONG).show();
        finish();
    }

    public void botonCancelar(View view) {
        finish();
    }

    public int restarFechas(java.util.Date hoy, java.util.Date fecha) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        long diferencia = ( hoy.getTime() - fecha.getTime() )/MILLSECS_PER_DAY;
        Log.e("dif-Dias","Diferencia de dias "+diferencia);
        return (int) diferencia;
    }

    //TAREA EN SEGUNDO PLANO CORREO O SMS
    public class Reservacion extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        Reservacion(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }
}
