package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.utilidades.ConexionSqliteHelper;

public class LoginActivity extends AppCompatActivity {
    EditText usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);
    }

    public void redirigir(View view){
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.btnRegistrar:
                miIntent = new Intent(LoginActivity.this,RegistroActivity.class);
                break;
        }
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }

    public void login(View view) {
        consultarUsuarioSql();
    }

    public void redirigirMenu(){
        Intent miIntent = null;
        miIntent = new Intent(LoginActivity.this,MenuActivity.class);
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }
    private void consultarUsuarioSql() {
        ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "db reservaDB", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {usuario.getText().toString()};
        try {
            Cursor cursor=db.rawQuery("SELECT "+ConexionSqliteHelper.CAMPO_USUARIO+","+ConexionSqliteHelper.CAMPO_PASSWORD
                    + " FROM "+ConexionSqliteHelper.TABLA_USUARIO+" WHERE "+ConexionSqliteHelper.CAMPO_USUARIO +"=? ",parametros);
            cursor.moveToFirst();
            System.out.println("***********************************"+cursor.getString(1));
            if(cursor.getString(1).equals(password.getText().toString())){
                redirigirMenu();
            }else{
                Toast.makeText(getApplicationContext(), "Password incorrecto", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
        }
    }
}
