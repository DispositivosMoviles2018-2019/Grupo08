package com.dldiaz.proyecto.ddiazexamen2h.modelo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

public class Vehiculo implements Serializable {

    private String placa;
    private String marca;
    private String fecFabricacion;
    private double costo;
    private boolean matriculado;
    private String color;
    private Bitmap foto;
    private boolean estado;
    private String tipo;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String marca, String fecFabricacion, double costo, boolean matriculado, String color, boolean estado, String tipo) {
        this.placa = placa;
        this.marca = marca;
        this.fecFabricacion = fecFabricacion;
        this.costo = costo;
        this.matriculado = matriculado;
        this.color = color;
        //this.foto = foto;
        this.estado = estado;
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFecFabricacion() {
        return fecFabricacion;
    }

    public void setFecFabricacion(String fecFabricacion) {
        this.fecFabricacion = fecFabricacion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Placa: "+ getPlaca() + "\n" +
                "Marca: " + getMarca()+ "\n" +
                "Fecha Fabricacion: " + getFecFabricacion()+ "\n" +
                "Costo: " + getCosto() + "\n" +
                "Matriculado: " + isMatriculado() + "\n" +
                "Color: " + getColor() + "\n" +
                "Estado: " + isEstado() + "\n" +
                "Tipo: " +  getTipo() ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehiculo)) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Double.compare(vehiculo.getCosto(), getCosto()) == 0 &&
                isMatriculado() == vehiculo.isMatriculado() &&
                isEstado() == vehiculo.isEstado() &&
                Objects.equals(getPlaca(), vehiculo.getPlaca()) &&
                Objects.equals(getMarca(), vehiculo.getMarca()) &&
                Objects.equals(getFecFabricacion(), vehiculo.getFecFabricacion()) &&
                Objects.equals(getColor(), vehiculo.getColor()) &&
                Objects.equals(getFoto(), vehiculo.getFoto()) &&
                Objects.equals(getTipo(), vehiculo.getTipo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaca(), getMarca(), getFecFabricacion(), getCosto(), isMatriculado(), getColor(), getFoto(), isEstado(), getTipo());
    }
}
