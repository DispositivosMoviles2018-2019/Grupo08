package ec.edu.uce.dvallejo_ex_1h.controlador;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ec.edu.uce.dvallejo_ex_1h.R;
import ec.edu.uce.dvallejo_ex_1h.modelo.ManejoArchivo;

/**
 * Created by BBOX on 28/11/2017.
 */

public class DialogoAuto extends DialogFragment {
    private static final String TAG = DialogoAuto.class.getSimpleName();

    private Context contexto;
    private VehiculoBean auto = new VehiculoBean();
    private int tipo;
    private int resultado;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> lista;
    private ArrayList<VehiculoBean> listadoBean;
    private int seleccionadoListaDatos;
    private int seleccionadoListaBean;
    private boolean error=false;
    private ManejoArchivo manejoArchivo;


    public ManejoArchivo getManejoArchivo() {
        return manejoArchivo;
    }

    public void setManejoArchivo(ManejoArchivo manejoArchivo) {
        this.manejoArchivo = manejoArchivo;
    }

    public int getSeleccionadoListaDatos() {
        return seleccionadoListaDatos;
    }

    public void setSeleccionadoListaDatos(int seleccionadoListaDatos) {
        this.seleccionadoListaDatos = seleccionadoListaDatos;
    }

    public int getSeleccionadoListaBean() {
        return seleccionadoListaBean;
    }

    public void setSeleccionadoListaBean(int seleccionadoListaBean) {
        this.seleccionadoListaBean = seleccionadoListaBean;
    }

    public ArrayList<VehiculoBean> getListadoBean() {
        return listadoBean;
    }

    public void setListadoBean(ArrayList<VehiculoBean> listadoBean) {
        this.listadoBean = listadoBean;
    }

    public ArrayAdapter<String> getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(ArrayAdapter<String> adaptador) {
        this.adaptador = adaptador;
    }

    public ArrayList<String> getLista() {
        return lista;
    }

    public void setLista(ArrayList<String> lista) {
        this.lista = lista;
    }

    public int getResultado() {
        return resultado;
    }

    public DialogoAuto( ) {
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context context) {
        this.contexto = context;
    }

    public VehiculoBean getAuto() {
        return auto;
    }

    public void setAuto(VehiculoBean auto) {
        this.auto = auto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    /**
     * Crea un diálogo con personalizado
     * @return Diálogo
     */
    public AlertDialog createLoginDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View vi = inflater.inflate(R.layout.content_automovil, null);

        builder.setView(vi);

        TextView txt = (TextView)vi.findViewById(R.id.lbl_titulo);
        final RadioButton rBlanco, rNegro, rOtro;

        rBlanco = (RadioButton)vi.findViewById(R.id.rd_blanco);
        rNegro = (RadioButton)vi.findViewById(R.id.rd_negro);
        rOtro = (RadioButton)vi.findViewById(R.id.rd_otro);

        RadioGroup radioGroup = (RadioGroup)vi.findViewById(R.id.rg_colores);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()            {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int selectedId) {
                //Log.e("RADIO",""+selectedId);
                if(rBlanco.isChecked() || rNegro.isChecked()) {
                    TextView txt = (TextView)vi.findViewById(R.id.txt_otro);
                    txt.setFocusable(false);
                }
                else {
                    TextView txt = (TextView) vi.findViewById(R.id.txt_otro);
                    txt.setFocusableInTouchMode(true);
                    txt.setFocusable(true);
                }
            }
        });

        //INSERTAR
        if(tipo==1) {
            txt.setText("INSERCION DE UN NUEVO AUTOMOVIL");
            rBlanco.setChecked(true); //seleccionamos el color blanco por defecto
        }
        //EDITAR
        else {
            txt.setText("EDICION DE UN AUTOMOVIL");

            EditText edit = (EditText)vi.findViewById(R.id.txt_placa);
            edit.setText(auto.getPlaca());
            edit.setFocusable(false);

            edit = (EditText)vi.findViewById(R.id.txt_marca);
            edit.setText(auto.getMarca());
            edit.setFocusable(false);

            edit = (EditText)vi.findViewById(R.id.txt_fecha);
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
            edit.setText(formateador.format(auto.getFechaFab()));
            edit.setFocusable(false);

            edit = (EditText)vi.findViewById(R.id.txt_costo);
            edit.setText( String.valueOf(auto.getCosto()) );

            edit = (EditText)vi.findViewById(R.id.txt_matriculado);
            if(auto.getMatriculado())
                edit.setText( "si" );
            else
                edit.setText( "no" );

            if(auto.getColor().equalsIgnoreCase("blanco")) {
                rBlanco.setChecked(true);
            } else if(auto.getColor().equalsIgnoreCase("negro")) {
                rNegro.setChecked(true);
            }
            else {
                rOtro.setChecked(true);
                edit = (EditText)vi.findViewById(R.id.txt_otro);
                edit.setText(auto.getColor());
            }
        }

        Button bt_ok = (Button) vi.findViewById(R.id.btn_ok);
        Button bt_cancel = (Button) vi.findViewById(R.id.btn_regresar);

        bt_cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resultado=0;
                        Toast mensaje = Toast.makeText(contexto.getApplicationContext(), "Cancelado!", Toast.LENGTH_LONG);
                        mensaje.show();
                        dismiss();
                    }
                }
        );

        bt_ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ok(vi);
                        resultado=1;
                        if(tipo==1) {
                            if(!error) {
                                Toast mensaje = Toast.makeText(contexto.getApplicationContext(), "OK nuevo auto ingresado!", Toast.LENGTH_LONG);
                                mensaje.show();
                            }
                        }
                        else {
                            if(!error) {
                                Toast mensaje = Toast.makeText(contexto.getApplicationContext(), "OK auto editado!", Toast.LENGTH_LONG);
                                mensaje.show();
                            }
                        }
                        if(!error)
                            dismiss();
                    }
                }

        );
        return builder.create();
    }

    //FUNCION QUE SE EJECUTA AL DAR CLIC EN EL BOTON OK
    public void ok(View view) {

        error = false;

        EditText edit = (EditText)view.findViewById(R.id.txt_placa);

        auto.setPlaca(edit.getText().toString());

        //validamos la placa
        Pattern pat = Pattern.compile("[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]");
        //Pattern pat = Pattern.compile("[A-Z][a-z][a-zA-Z]-[0-9][0-9]");
        Matcher mat = pat.matcher(edit.getText().toString());
        if(mat.matches()) {
            auto.setPlaca(edit.getText().toString());
        }
        else {
            Toast.makeText(view.getContext(), "Error, formato de placa incorrecto!" , Toast.LENGTH_LONG).show();
            error = true;
        }

        edit = (EditText)view.findViewById(R.id.txt_marca);
        auto.setMarca(edit.getText().toString());

        edit = (EditText)view.findViewById(R.id.txt_fecha);
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd"); //"dd/MM/yy" "dd-MM-yy"
        Date date = null;

        Pattern pat2 = Pattern.compile("[0-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]");
        Matcher mat2 = pat2.matcher(edit.getText().toString());
        if(mat2.matches()) {
            try {
                date = formateador.parse(edit.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(view.getContext(),
                        "Error, formato de fecha no valido!" , Toast.LENGTH_LONG)
                        .show();
                date = new Date("1990/01/01");
                error = true;
            }
            auto.setFechaFab(date);
        }
        else {
            Toast.makeText(view.getContext(), "Error, formato de fecha incorrecto!" , Toast.LENGTH_LONG).show();
            error = true;
        }

        edit = (EditText)view.findViewById(R.id.txt_costo);
        try {
            auto.setCosto(Double.parseDouble(edit.getText().toString()));
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Error, valor de Costo erroneo!" , Toast.LENGTH_LONG).show();
            error = true;
        }

        edit = (EditText)view.findViewById(R.id.txt_matriculado);
        if(edit.getText().toString().equalsIgnoreCase("si"))
            auto.setMatriculado(true);
        else
            auto.setMatriculado(false);

        //COLOR
        RadioButton rBlanco, rNegro, rOtro;

        rBlanco = (RadioButton)view.findViewById(R.id.rd_blanco);
        rNegro = (RadioButton)view.findViewById(R.id.rd_negro);
        rOtro = (RadioButton)view.findViewById(R.id.rd_otro);

        if(rBlanco.isChecked())
            auto.setColor("Blanco");
        if(rNegro.isChecked())
            auto.setColor("Negro");

        if(rOtro.isChecked()) {
            edit = (EditText)view.findViewById(R.id.txt_otro);
            auto.setColor(edit.getText().toString());
        }

        if(!error) {
            if (tipo == 1) {
                lista.add("Vehiculo "+"\n\r" + auto.toString());
                adaptador.notifyDataSetChanged(); //notificamos a la lista para que actualice los datos
                listadoBean.add(auto); //añadimos un auto a la lista

            } else {
                listadoBean.remove(seleccionadoListaBean);
                listadoBean.add(auto);

                lista.remove(seleccionadoListaDatos);
                lista.add("Vehiculo " + "\n\r" + auto.toString());
                adaptador.notifyDataSetChanged();
            }
            manejoArchivo.guardaArchivo("autos.json");
        }
    }
}
