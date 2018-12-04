package ec.edu.uce.controlador;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.componentes.StorageException;
import ec.edu.uce.modelo.Usuario;

public class Manejo_Usuario {

    private final String FILE_NAME = "usuarios.txt";
    private ObjectMapper MAPPER = new ObjectMapper();
    
    private Manejo_Archivo manejoUsuarios = new Manejo_Archivo();
    private File usuarioFile;

    public Manejo_Usuario() {
        this.usuarioFile = manejoUsuarios.getFile(FILE_NAME);
    }

    public void initResources(Context context) {
        try {
            if (!manejoUsuarios.existBaseFolder()) {
                manejoUsuarios.createBaseFolder();
                
            }
            if (!existFile()) {
                manejoUsuarios.createFile(FILE_NAME);

            }
        } catch (StorageException e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    public Usuario save(Usuario usuario) {
        List<Usuario> usuarios = getUsuarios();
        usuarios.add(usuario);
        try {
            manejoUsuarios.writeFile(usuarioFile, MAPPER.writeValueAsString(usuarios));
            return usuario;
        } catch (JsonProcessingException e) {
            throw new CustomException("Error al guardar los datos del usuarios", e);
        }
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        if (existFile()) {
            String registerData = manejoUsuarios.readFile(usuarioFile);
            if (!registerData.isEmpty()) {
                try {
                    usuarios = MAPPER.readValue(registerData, new TypeReference<List<Usuario>>() {
                    });
                } catch (IOException e) {
                    throw new CustomException("Error al leer el archivo: " + FILE_NAME, e);
                }
            }
        }
        return usuarios;
    }

    public Usuario find(Usuario usuario) {
        List<Usuario> usuarios = getUsuarios();

        for (Usuario usr : usuarios) {
            if (usr.equals(usuario)) {
                return usr;
            }
        }
        return null;
    }

    public boolean existFile() {
        return manejoUsuarios.existFile(FILE_NAME);
    }

    public boolean exist(Usuario usuario) {
        return getUsuarios().contains(usuario);
    }
}
