package ec.edu.uce.controlador;

import android.os.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ec.edu.uce.componentes.StorageException;

public class Manejo_Archivo {

    private final File ROOT_PATH = Environment.getExternalStorageDirectory();
    private final String FOLDER_NAME = "Vehiculos";
    private ObjectMapper MAPPER = new ObjectMapper();

    public File getBaseFolder() {
        return new File(ROOT_PATH, FOLDER_NAME);
    }

    public boolean existBaseFolder() {
        return getBaseFolder().exists();
    }

    public void createBaseFolder() {
        if (!getBaseFolder().mkdirs()) {
            throw new StorageException("Error al crear la carpeta: " + FOLDER_NAME);
        }
    }

    public boolean existFile(String fileName) {
        return new File(getBaseFolder(), fileName).exists();
    }

    public void createFile(String fileName) {
        File file = new File(getBaseFolder(), fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error al crear el archivo: " + fileName, e);
        }
    }

    public File getFile(String fileName) {
        return new File(getBaseFolder(), fileName);
    }

    public String readFile(File file) {
        String data = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                data += line;
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new StorageException("No existe el archivo: " + file.getName(), e);
        } catch (IOException e) {
            throw new StorageException("Ocurrio un error al leer el archivo: " + file.getName(), e);
        }
        return data;
    }

    public void writeFile(File file, String data) {
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.print(data);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            throw new StorageException("Error al escribir en el archivo: " + file.getName());
        }
    }

    public void guardarArchivo(File file,String dato){
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);

            pw.print(dato);
            pw.flush();
            pw.close();
            fw.close();
            /*
            File ruta_sd= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File f=new File (ruta_sd.getAbsolutePath(),"archivo."+tipoarchivo);
            //  Toast.makeText(getBaseContext(),""+ruta_sd,Toast.LENGTH_SHORT).show();
            OutputStreamWriter archivo= new OutputStreamWriter(new FileOutputStream(f));

            archivo.write(dato);
            archivo.flush();
            archivo.close();
             Toast t=Toast.makeText(this,"Datos Guardados"+ruta_sd,Toast.LENGTH_SHORT);
             t.show();*/
        } catch (IOException e){
            throw new StorageException("Error al escribir en el archivo: " + file.getName());
        }
    }

}
