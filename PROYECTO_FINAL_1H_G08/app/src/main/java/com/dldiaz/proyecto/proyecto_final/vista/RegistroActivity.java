package com.dldiaz.proyecto.proyecto_final.vista;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dldiaz.proyecto.proyecto_final.R;

public class RegistroActivity extends AppCompatActivity {
    EditText usuario,clave;
    Button guardarB;

    static final int READ_BLOCK_SIZE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);
        usuario = (EditText) findViewById(R.id.usuario);
        clave = (EditText) findViewById(R.id.clave);
        guardarB = (Button) findViewById(R.id.btnGuardar);

        guardarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("DATOS",MODE_PRIVATE);
                String newUsuario = usuario.getText().toString();
                String newClave = clave.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(newUsuario,newUsuario);
                editor.commit();
                editor.putString(newClave,newClave);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Registrado Correctamente",
                        Toast.LENGTH_SHORT).show();
                Intent regresoPantalla = new Intent(RegistroActivity.this,MainActivity.class);
                startActivity(regresoPantalla);

            }

        });

    }

}
