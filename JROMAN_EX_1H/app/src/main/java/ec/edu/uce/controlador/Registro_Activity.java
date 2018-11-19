package ec.edu.uce.controlador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.R;
import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Usuario;

public class Registro_Activity extends AppCompatActivity {

    private Manejo_Usuario manejoUsuario = new Manejo_Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void register(View view) {
        EditText txtUsername = findViewById(R.id.txtusername);
        EditText txtPassword = findViewById(R.id.txtPassword);

        try {
            manejoUsuario.initResources(this);

            Usuario usuario = new Usuario(txtUsername.getText().toString(), txtPassword.getText().toString());
            if (manejoUsuario.exist(usuario)) {
                Toast.makeText(this, "El usuario " + usuario.getUsername() + " ya existe ", Toast.LENGTH_LONG).show();
            } else {
                manejoUsuario.save(usuario);
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        }catch (CustomException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
