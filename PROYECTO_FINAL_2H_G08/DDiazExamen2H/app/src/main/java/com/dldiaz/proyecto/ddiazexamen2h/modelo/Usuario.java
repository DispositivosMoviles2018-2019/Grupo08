package com.dldiaz.proyecto.ddiazexamen2h.modelo;

import java.util.Objects;

public class Usuario {

    private String usuario;
    private String password;

    public Usuario() {
    }

    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(getUsuario(), usuario1.getUsuario()) &&
                Objects.equals(getPassword(), usuario1.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuario(), getPassword());
    }
}
