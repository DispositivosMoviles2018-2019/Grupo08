package com.dldiaz.proyecto.ddiazexamen2h.vista;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.BottomNavigationView;
        import android.support.v4.app.Fragment;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.dldiaz.proyecto.ddiazexamen2h.R;
        import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;


        import java.util.ArrayList;

public class VehiculoActivity extends AppCompatActivity {
    Button crearVehiculo;
    Button elimnarButton,actualizar;
    ArrayList<String> listaInformacion;
    private static final String EXTRA_PLACA = "com.dldiaz.proyecto.ddiazexamen2h.placaId";
    private static final String EXTRA_UPDATE = "com.dldiaz.proyecto.ddiazexamen2h.add";
   private VehiculoImpl vehiculoImpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehiculo);
       // elimnarButton =(Button)findViewById(R.id.eliminar);
       // actualizar = (Button) findViewById(R.id.btnActualizarVehiculo);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_vehiculos);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

//        elimnarButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPlacaAndDeleteVehi();
//            }
//        });
//        actualizar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getPlacaAndUpdateVehi();
//            }
//        });
        //ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
       // miLista.setAdapter(adaptador);
        //guardarFecha();
        //miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        /*    @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion="Placa: "+ listaVehiculos.get(position).getPlaca()+"\n";
                informacion+= "Marca: " + listaVehiculos.get(position).getMarca();
                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();
                Vehiculo vehiculo = listaVehiculos.get(position);

            } */
        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menu_listar:
                          selectedFragment = new VehiculoListarFragment();
                            break;
                        case R.id.menu_crear:
                            selectedFragment = new VehiculoCrearFragment();
                            break;
                        case R.id.menu_buscar_actualizar:
                            selectedFragment = new VehiculoActualizarFragment();
                            break;
                        case R.id.menu_eliminar:
                            selectedFragment = new Fragment();
                            getPlacaAndDeleteVehi();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_vehiculo,
                            selectedFragment).commit();

                    return true;
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

  /*  public void insertarV(View view) {
        insertarVehiculo();

    }*/


    public void redirigirIngresarVehiculo(View view){
        Intent miIntent = null;
        miIntent = new Intent(VehiculoActivity.this,VehiculoCrearFragment.class);
        if (miIntent!=null){
            startActivity(miIntent);
        }
    }
    public void getPlacaAndUpdateVehi(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View getPlaca = layoutInflater.inflate(R.layout.dialog_get_placa,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(getPlaca);
        final EditText placaInput = (EditText) getPlaca.findViewById(R.id.editTextDialogUserInput);
        dialog
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new VehiculoCrearFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("Actualizar", "Update");
                        System.out.println("******-*--*-LLEgo a la obtencion de placa");
                      //  bundle.putString(EXTRA_PLACA,placaInput.getText().toString());
                        VehiculoCrearFragment myFrag = new VehiculoCrearFragment();
                        myFrag.setArguments(bundle);
                    }
                }).create().show();
    }
    public void getPlacaAndDeleteVehi(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View getPlaca = layoutInflater.inflate(R.layout.dialog_get_placa,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(getPlaca);
        final EditText placaInput = (EditText) getPlaca.findViewById(R.id.editTextDialogUserInput);
        System.out.println("placana borrar"+placaInput.getText().toString());
        dialog
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vehiculoImpl = new VehiculoImpl(VehiculoActivity.this);
                        vehiculoImpl.open();
                        vehiculoImpl.borrar(vehiculoImpl.getVehiculo(placaInput.getText().toString()));
                        Toast t = Toast.makeText(VehiculoActivity.this,"Vehiculo eliminado exitosamente!!",Toast.LENGTH_SHORT);
                        vehiculoImpl.close();
                    }
                }).create().show();
    }
//    @Override
//    protected void OnResume(){
//        super.onResume();
//        vehiculoImpl.open();
//    }
//
//    protected void OnPause(){
//        super.onPause();
//        vehiculoImpl.close();
//   }

}
