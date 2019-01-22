package ec.edu.uce.exa_2h_jroman.vista;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ec.edu.uce.exa_2h_jroman.R;
import ec.edu.uce.exa_2h_jroman.controlador.VehiculoAdapter;

public class Menu extends AppCompatActivity {

    VehiculoAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    /*OPCION VEHICULOS: Lleva al activity ListaActivity*/
    public void vehiculos(View view) {
        Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
        startActivity(intent);
        //finish();
    }
    /*OPCION RESERVASS: Lleva al activity ReservarActivity*/
    public void reservas(View view) {
        Intent intent = new Intent(getApplicationContext(), ReservarActivity.class);
        startActivity(intent);
        //finish();
    }
}
