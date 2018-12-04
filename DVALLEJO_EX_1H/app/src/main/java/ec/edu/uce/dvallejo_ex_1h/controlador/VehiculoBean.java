package ec.edu.uce.dvallejo_ex_1h.controlador;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BBOX on 27/11/2017.
 */

public class VehiculoBean implements Serializable {

    String placa="";
    String marca="";
    Date fechaFab=null;
    double costo=0;
    boolean matriculado=false;
    String color="";

    public VehiculoBean() {
        super();
    }

    public VehiculoBean(String placa, String marca, Date fechaFab, double costo, boolean matriculado, String color) {
        this.placa = placa;
        this.marca = marca;
        this.fechaFab = fechaFab;
        this.costo = costo;
        this.matriculado = matriculado;
        this.color = color;
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

    public Date getFechaFab() {
        return fechaFab;
    }

    public void setFechaFab(Date fechaFab) {
        this.fechaFab = fechaFab;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean getMatriculado() {
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

    @Override
    public String toString() {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
        String str;

        str =  " Placa: " + placa + "\n\r"+
                " Marca: " + marca + "\n\r"+
                " Fecha de Fabricación: " + formateador.format(fechaFab) + "\n\r"+
                " Costo: " + costo + "\n\r";
        if(matriculado)
            str += " Matriculado: si\n\r";
        else
            str += " Matriculado: no\n\r";
        str += " Color: " + color + "\n\r";
        return str;
    }

}
