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

public class MainActivity extends AppCompatActivity {
    EditText usuario,clave;
    Button ingresar,registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = (EditText) findViewById(R.id.usuarioIngreso);
        clave = (EditText) findViewById(R.id.claveIngreso);
        ingresar = (Button) findViewById(R.id.btnIngresar);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuarioI = usuario.getText().toString();
                String claveI = clave.getText().toString();

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("DATOS", MODE_PRIVATE);
                String usuarioM = preferences.getString(usuarioI,"No existe informacion del usuario ingresado");
                String claveM = preferences.getString(claveI,"No existe informacion del usuario ingresado");
                if(usuarioM.equals(usuarioI) && claveM.equals(claveI)){
                    Intent datosVehiculo = new Intent(MainActivity.this, DatosVehiculoActivity.class );
                    startActivity(datosVehiculo);
                }else{
                    Toast.makeText(getApplicationContext(), "Credenciales no validas.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registroPantalla = new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(registroPantalla);
            }
        });
    }



}
