package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.utilidades.ConexionSqliteHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {
    EditText usuario, password, password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);
        password1 = (EditText) findViewById(R.id.password1);
    }

    public void registrar(View view) {
        registrarUsuariosSql();
    }
    public void redirigirLogin(){
        Intent miIntent = null;
        miIntent = new Intent(RegistroActivity.this,LoginActivity.class);
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }
    private void registrarUsuariosSql() {
        ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "db reservaDB", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        if(consultarSql()){
            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
        }else{
            System.out.println("pass"+ password.getText().toString()+"pass1"+password1.getText());
            if(password.getText().toString().equals(password1.getText().toString()) && validarContrasena(password.getText().toString())){
       //     if(password.getText().toString().equals(password1.getText().toString())){
                String insert = "INSERT INTO " + ConexionSqliteHelper.TABLA_USUARIO + "(" + ConexionSqliteHelper.CAMPO_USUARIO + "," + ConexionSqliteHelper.CAMPO_PASSWORD + ")" +
                        " VALUES('"+ usuario.getText().toString() + "','" + password.getText().toString() + "')";
                db.execSQL(insert);
                System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<:" + insert);
                //Toast.makeText(getApplicationContext(), "Usuario: " + nombre.getText().toString() + "Registrado", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                db.close();
                redirigirLogin();

            }else{
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden o longitud no permitida", Toast.LENGTH_LONG).show();
                password.setText("");
                password1.setText("");
            }

        }
    }

    private boolean validarContrasena(String contrasena){
        Pattern restriccion = Pattern.compile("[A-Z]");
        Matcher validar = restriccion.matcher(contrasena);
        if(!validar.find()){
            return false;
        }
        if(contrasena.length()==8) {
            return true;
        }else {
            return false;
        }
    }
    private boolean consultarSql() {
        ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "db reservaDB", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {usuario.getText().toString()};
        try {
            Cursor cursor=db.rawQuery("SELECT "+ConexionSqliteHelper.CAMPO_USUARIO+","+ConexionSqliteHelper.CAMPO_PASSWORD
                    + " FROM "+ConexionSqliteHelper.TABLA_USUARIO+" WHERE "+ConexionSqliteHelper.CAMPO_USUARIO +"=? ",parametros);
            cursor.moveToFirst();

            usuario.setText(cursor.getString(0));
            password.setText(cursor.getString(1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
