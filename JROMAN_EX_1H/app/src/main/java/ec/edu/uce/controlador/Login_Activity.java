package ec.edu.uce.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.modelo.Usuario;

public class Login_Activity extends AppCompatActivity {

    private Manejo_Usuario manejoUsuario = new Manejo_Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void registerActivity(View view) {
        Intent intent = new Intent(this, Registro_Activity.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText txtUsername = findViewById(R.id.txtusername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        Usuario usuario = manejoUsuario.find(new Usuario(txtUsername.getText().toString(), null));

        if (usuario != null) {
            if (usuario.getPassword().equals(txtPassword.getText().toString())) {
                Intent intent = new Intent(Login_Activity.this, ListaVehiculos_Activity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "El usuario no existe !", Toast.LENGTH_LONG).show();
        }
    }
}
