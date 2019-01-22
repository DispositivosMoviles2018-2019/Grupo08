package ec.edu.uce.tarea_final_2h_hguaman.vista;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ec.edu.uce.tarea_final_2h_hguaman.R;
import ec.edu.uce.tarea_final_2h_hguaman.controlador.VehiculoAdapter;


public class Menu extends AppCompatActivity {

    VehiculoAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void vehiculos(View view) {
        Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
        startActivity(intent);
        //finish();
    }

    public void reservas(View view) {
        Intent intent = new Intent(getApplicationContext(), ReservarActivity.class);
        startActivity(intent);
        //finish();
    }
}
