package com.dldiaz.proyecto.proyecto_final.controlador;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
    public static void writeToFile(Context context, String fileName, String str){
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            fos.write(str.getBytes(),0,str.length());
            fos.close();
            System.out.println(">>>>>>>>Archivo creado con nombre" + fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void writeToFile(File f,String str)  throws IOException{
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(str.getBytes());
            fos.close();
    }
    public static String stringFromStream(InputStream is){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            System.out.println(">>>>>><Leyendo el archivo");
            StringBuilder sb= new StringBuilder();
            String line = null;
            while ((line = reader.readLine())!= null)
                sb.append(line).append("\n");
            reader.close();
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
