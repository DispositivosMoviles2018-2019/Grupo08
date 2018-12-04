package com.dldiaz.proyecto.proyecto_final.modelo;

public class Vehiculo {
    private String placa;
    private String marca;
    private String fecFabricacion;
    private int costo;
    private boolean matriculado;
    private String color;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String marca, String fecFabricacion, int costo, boolean matriculado, String color) {
        this.placa = placa;
        this.marca = marca;
        this.fecFabricacion = fecFabricacion;
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

    public String getFecFabricacion() {
        return fecFabricacion;
    }

    public void setFecFabricacion(String fecFabricacion) {
        this.fecFabricacion = fecFabricacion;
    }

    public int getCosto() {
        return costo;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String toJsonString(){
        return  "{ \"placa\": \"ABC123\",\n"+
                " \"costo\": 5444,\n"+
                " \"fecFabricacion\": \"15\",\n"+
                " \"marca\": Mazda,\n"+
                " \"matriculado\": TRUE,\n"+
                " \"placa\": \"ADE232\"}";
    }
}
