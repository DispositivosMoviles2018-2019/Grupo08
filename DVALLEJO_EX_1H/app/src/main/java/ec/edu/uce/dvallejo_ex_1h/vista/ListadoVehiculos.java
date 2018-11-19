package ec.edu.uce.dvallejo_ex_1h.vista;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ec.edu.uce.dvallejo_ex_1h.R;
import ec.edu.uce.dvallejo_ex_1h.controlador.DialogoAuto;
import ec.edu.uce.dvallejo_ex_1h.controlador.VehiculoBean;
import ec.edu.uce.dvallejo_ex_1h.modelo.ManejoArchivo;

public class ListadoVehiculos extends AppCompatActivity {

    static private ManejoArchivo manejoArchivo = new ManejoArchivo();
    int itemSelec=AdapterView.INVALID_POSITION;
    private ArrayAdapter<String> adaptador;
    DialogoAuto dialog = new DialogoAuto();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_vehiculos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //cargar archivo JSON
        manejoArchivo.setContext(getApplicationContext());
        manejoArchivo.leeArchivo(getApplicationContext(), "autos.json");


        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, manejoArchivo.getListadoDatos());

        final ListView lista = (ListView)findViewById(R.id.lst_lista);

        lista.setAdapter(adaptador);

        // ListView Item Click Listener
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                itemSelec = itemPosition;

                //ListAdapter la = (ListAdapter) parent.getAdapter();

                ArrayList<View> touchables = parent.getTouchables();

                for (View v : touchables){
                    if (v instanceof TextView){
                        v.setBackgroundColor(0xffffffff);
                    }
                }
                view.setBackgroundColor(0xff999999);
            }

        });

        //PRESION LARGA EN EUN ELEMENTO DE LA LISTA
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final int posicion=i;

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ListadoVehiculos.this);
                dialogo1.setTitle("ATENCION");
                dialogo1.setMessage("¿Eliminar este vehiculo?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        ArrayList<VehiculoBean> listaB = manejoArchivo.getListadoBean();

                        String dat = lista.getItemAtPosition(posicion).toString();

                        //LEEMOS LA PLACA DEL ELEMENTO SELECCIONADO EN LA LISTA
                        int ind = dat.indexOf("Placa");
                        String placa = dat.substring(ind+7, ind+7+8);

                        for (int i=0; i<listaB.size(); i++) {
                            if(listaB.get(i).getPlaca().equals(placa)) {
                                manejoArchivo.getListadoBean().remove(i);
                                break;
                            }
                        }

                        manejoArchivo.getListadoDatos().remove(posicion);
                        adaptador.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });
    }

    public void ingresar(View view) {
        dialog.setTipo(1); //Tipo INSERCION

        dialog.setContexto(getApplicationContext());
        dialog.setLista(manejoArchivo.getListadoDatos());
        dialog.setAdaptador(adaptador);
        dialog.setListadoBean(manejoArchivo.getListadoBean());
        dialog.setManejoArchivo(manejoArchivo);

        dialog.show(getFragmentManager(),"INSERCION");
    }

    public void editar(View view) {
        ListView lis = (ListView)findViewById(R.id.lst_lista);

        if(itemSelec == AdapterView.INVALID_POSITION) {
            Toast.makeText(getApplicationContext(), "Primero debe seleccionar un item de la lista!" , Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<VehiculoBean> listaB = manejoArchivo.getListadoBean();

        String dat = lis.getItemAtPosition(itemSelec).toString();

        //LEEMOS LA PLACA DEL ELEMENTO SELECCIONADO EN LA LISTA
        int ind = dat.indexOf("Placa");
        String placa = dat.substring(ind+7, ind+7+8);
        VehiculoBean auto=null;

        for (int i=0; i<listaB.size(); i++) {
            if(listaB.get(i).getPlaca().equals(placa)) {
                auto = listaB.get(i);
                dialog.setSeleccionadoListaBean(i);
                break;
            }
        }

        dialog.setTipo(2); //Tipo EDITAR

        dialog.setContexto(getApplicationContext());
        dialog.setLista(manejoArchivo.getListadoDatos());
        dialog.setSeleccionadoListaDatos(itemSelec);
        dialog.setAdaptador(adaptador);
        dialog.setListadoBean(manejoArchivo.getListadoBean());
        dialog.setAuto(auto);
        dialog.setManejoArchivo(manejoArchivo);

        dialog.show(getFragmentManager(),"EDICION");
    }

    public void salir(View view) {
        manejoArchivo.guardaArchivo("datos.json");
        finish();
    }
}
