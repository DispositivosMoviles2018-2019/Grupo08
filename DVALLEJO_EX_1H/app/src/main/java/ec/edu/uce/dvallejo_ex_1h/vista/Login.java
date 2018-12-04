package ec.edu.uce.dvallejo_ex_1h.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ec.edu.uce.dvallejo_ex_1h.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ingresar(View view) {
        TextView user = (TextView)findViewById(R.id.txt_usuario);
        TextView clave = (TextView)findViewById(R.id.txt_clave);

        if(user.getText().toString().equals("diego") && clave.getText().toString().equals("vallejo") ) {
            Intent vehiculos = new Intent(getApplicationContext(), ListadoVehiculos.class);
            startActivity(vehiculos);
        }
        else {
            Toast mensaje = Toast.makeText(getApplicationContext(), "Error usuario/clave incorrectos!", Toast.LENGTH_LONG);
            mensaje.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast mensaje = Toast.makeText(getApplicationContext(), "PAUSA", Toast.LENGTH_LONG);
        //mensaje.show();
        finish();
    }
}
