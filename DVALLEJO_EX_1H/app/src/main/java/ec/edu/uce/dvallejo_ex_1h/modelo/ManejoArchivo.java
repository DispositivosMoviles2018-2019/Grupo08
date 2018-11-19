package ec.edu.uce.dvallejo_ex_1h.modelo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ec.edu.uce.dvallejo_ex_1h.controlador.VehiculoBean;

/**
 * Created by BBOX on 27/11/2017.
 */

public class ManejoArchivo implements Serializable {
    private ArrayList<String> listadoDatos = new ArrayList<String>();
    private ArrayList<VehiculoBean> listadoBean = new ArrayList<VehiculoBean>();
    private Context context;
    private FileReader fr=null;

    public ManejoArchivo() {

    }

    public ArrayList<VehiculoBean> getListadoBean() {
        return listadoBean;
    }

    public void setListadoBean(ArrayList<VehiculoBean> listadoBean) {
        this.listadoBean = listadoBean;
    }

    public ArrayList<String> getListadoDatos() {
        return listadoDatos;
    }

    public void setListadoDatos(ArrayList<String> listadoDatos) {
        this.listadoDatos = listadoDatos;
    }

    public void verificaArchivo(String nombre_archivo) {
        //File tarjeta = Environment.getExternalStorageDirectory();
        File tarjeta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = new File( tarjeta.getAbsolutePath(), nombre_archivo );

        //no existe el archivo
        if (!file.exists()) {
            //OutputStreamWriter fout=null;
            try {

                file = new File(tarjeta.getAbsolutePath(), nombre_archivo);
                OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));

                //fout = new OutputStreamWriter(context.getApplicationContext().openFileOutput(Environment.getExternalStorageDirectory()+"/"+nombre_archivo, Activity.MODE_WORLD_WRITEABLE));
                //Log.e("ESC",fout. .getAbsolutePath());
                fout.write("{\n\"TotalV\":2,\n\"Vehiculo 1\":\n{\n\"Placa\": \"PSP-2343\",\n\"Marca\": \"Chevrolet\",\n\"FechaF\": \"1999/01/01\",\n\"Costo\": 12000.15,\n\"Matriculado\": \"true\",\n\"Color\": \"Blanco\"\n},\n\"Vehiculo 2\":\n{\n\"Placa\": \"JFC-3487\",\n\"Marca\": \"Mazda\",\n\"FechaF\": \"2010/01/01\",\n\"Costo\": 25400.13,\n\"Matriculado\": \"false\",\n\"Color\": \"Negro\"\n}\n}\n");
                fout.close();
            } catch (Exception e) {
                Toast mensaje = Toast.makeText(context.getApplicationContext(), "Error creando archivo por primera vez. "+e.toString(), Toast.LENGTH_LONG);
                mensaje.show();
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<String> leeArchivo(Context context, String nombre_archivo)  {

        this.context = context;  //guardo el contexto de la aplicacion

        VehiculoBean auto;

        //verificaArchivo(nombre_archivo);

        try {
            //File tarjeta = Environment.getExternalStorageDirectory();
            File tarjeta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            fr = new FileReader( new File(tarjeta.getAbsolutePath(), nombre_archivo) );
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al abrir fichero de memoria interna");
            //Toast mensaje = Toast.makeText(context.getApplicationContext(), "Error al abrir fichero de memoria interna "+e.toString(), Toast.LENGTH_LONG);
            //mensaje.show();
            return null;
        }

        try {
            String tot="", cadena;
            BufferedReader b = new BufferedReader(fr);

            //leemos el contenido del archivo
            while((cadena = b.readLine())!=null) {
                System.out.println(cadena);
                tot += cadena;
                tot += "\n";
            }
            b.close();

            JSONObject reader = new JSONObject(tot);

            int cont = 1;
            double val=0;
            boolean mat;
            String data, item;

            int total = (int)reader.getInt("TotalV");
            Toast mensaje = Toast.makeText(context, "Total vehiculos: "+total, Toast.LENGTH_LONG);
            mensaje.show();

            SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");

            for (int i=0; i<total; i++) {
                item = "";
                JSONObject innerObject = (JSONObject) reader.get("Vehiculo " + cont);
                auto = new VehiculoBean();

                item = "Vehiculo ";

                data = (String) innerObject.get("placa");
                auto.setPlaca(data);
                //System.out.println(data);
                data = (String) innerObject.get("marca");
                auto.setMarca(data);
                //System.out.println(data);

                data = (String) innerObject.get("fechaFab");
                Date d = new Date(data);
               //System.out.println(data);
                //String DateToStr = formateador.format(d);
                auto.setFechaFab(d);



                val = (double) innerObject.getDouble("costo");
                auto.setCosto(val);
                //System.out.println(val);
                mat = (boolean) innerObject.getBoolean("matriculado");
                auto.setMatriculado(mat);
                //System.out.println(data);
                data = (String) innerObject.get("color");
                auto.setColor(data);
                //System.out.println(data);

                //aumento propietario
                //data = (String) innerObject.get("Propietario");
                //auto.setPropietario(data);

                item += "\n\r" + auto.toString() + "\n\r";

                listadoDatos.add(item);
                listadoBean.add(auto);

                cont++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            Toast mensaje = Toast.makeText(context, "error leyendo json: "+e.toString(), Toast.LENGTH_LONG);
            mensaje.show();
        }

        return listadoDatos;
    }

    public void guardaArchivo(String nombre_archivo) {
        Gson gson = new Gson();
        String jsonString;
        VehiculoBean auto = new VehiculoBean();

        //OutputStreamWriter fout=null;

        try {

            //File tarjeta = Environment.getExternalStorageDirectory();
            File tarjeta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File file = new File(tarjeta.getAbsolutePath(), nombre_archivo);
            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(file));

            //fout = new OutputStreamWriter(context.getApplicationContext().openFileOutput(Environment.getExternalStorageDirectory()+"/"+nombre_archivo, Activity.MODE_WORLD_WRITEABLE));
            fout.write("{\"TotalV\":"+listadoBean.size()+",\n\r");

            for(int i=0; i<listadoBean.size(); i++) {
                jsonString = "\"Vehiculo "+(i+1)+"\": ";

                auto = listadoBean.get(i);

                jsonString += gson.toJson(auto);

                Log.e("FILE", jsonString.toString());

                if(i<listadoBean.size()-1)
                    jsonString += ",\n\r";
                fout.write(jsonString);
                Log.e("JSON: "+(i+1), jsonString);
            }
            fout.write("}\n\r");
            fout.close();
        } catch (Exception e) {
            Toast mensaje = Toast.makeText(context.getApplicationContext(), "Error grabando archivo "+e.toString(), Toast.LENGTH_LONG);
            mensaje.show();
        }
    }
}
