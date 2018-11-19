package ec.edu.uce.controlador;

import android.content.Context;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.StorageException;
import ec.edu.uce.modelo.Vehiculo;

public class Manejo_Vehiculos {

    private final String FILE_NAME = "vehiculos.json";
    private ObjectMapper MAPPER = new ObjectMapper();

    private Manejo_Archivo manejoUsuarios = new Manejo_Archivo();
    private File vehiculoFile;

    public Manejo_Vehiculos() {
        this.vehiculoFile = manejoUsuarios.getFile(FILE_NAME);
    }

    public void initResources(Context context) {
        try {
            if (!manejoUsuarios.existFile(FILE_NAME)) {
                manejoUsuarios.createFile(FILE_NAME);
                Toast.makeText(context, "Creando el archivo: " + FILE_NAME, Toast.LENGTH_LONG).show();
            }
        } catch (StorageException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public List<Vehiculo> save(List<Vehiculo> vehiculos) {
        try {
            manejoUsuarios.writeFile(vehiculoFile, MAPPER.writeValueAsString(vehiculos));
            return getVehiculos();
        } catch (JsonProcessingException e) {
            throw new CustomException("Error al guardar los datos del vehiculo", e);
        }
    }

    public List<Vehiculo> guardarJson(List<Vehiculo>listavehiculos) {
        String archivojason = "";
        try {
            JSONArray jsonArray = new JSONArray();
            for (Vehiculo vehiculo : listavehiculos) {
                JSONObject objeto = new JSONObject();
                objeto.put("placa", vehiculo.getPlaca());
                objeto.put("marca", vehiculo.getMarca());
                objeto.put("fechafabricacion", vehiculo.getFechaFabricacion());
                objeto.put("costo", vehiculo.getCosto());
                objeto.put("matriculado", vehiculo.getMatriculado());
                objeto.put("color", vehiculo.getColor());
                jsonArray.put(objeto);

            }
            archivojason = jsonArray.toString();
            manejoUsuarios.guardarArchivo(vehiculoFile, archivojason);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getVehiculos();
    }

        public List<Vehiculo> getVehiculos() {
        String vehiculoData = manejoUsuarios.readFile(vehiculoFile);

        List<Vehiculo> vehiculos = new ArrayList<>();
        if (!vehiculoData.isEmpty()) {
            try {
                vehiculos = MAPPER.readValue(vehiculoData, new TypeReference<List<Vehiculo>>() {
                });
            } catch (IOException e) {
                throw new CustomException("Error al leer el archivo: " + FILE_NAME, e);
            }
        }
        return vehiculos;
    }

    public boolean existFile() {
        return manejoUsuarios.existFile(FILE_NAME);
    }
}
