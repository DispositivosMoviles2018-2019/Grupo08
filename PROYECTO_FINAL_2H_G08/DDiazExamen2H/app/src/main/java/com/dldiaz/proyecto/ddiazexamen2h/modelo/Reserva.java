package com.dldiaz.proyecto.ddiazexamen2h.modelo;

public class Reserva {
    private String id;
    private String email;
    private String celular;
    private String fecha_pestramo;
    private String fecha_entrega;
    private int valor_reserva;
    private String placa;

    public Reserva() {}

    public Reserva(String id, String email, String celular, String fecha_pestramo, String fecha_entrega, int valor_reserva, String placa) {
        this.id = id;
        this.email = email;
        this.celular = celular;
        this.fecha_pestramo = fecha_pestramo;
        this.fecha_entrega = fecha_entrega;
        this.valor_reserva = valor_reserva;
        this.placa = placa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFecha_pestramo() {
        return fecha_pestramo;
    }

    public void setFecha_pestramo(String fecha_pestramo) {
        this.fecha_pestramo = fecha_pestramo;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public int getValor_reserva() {
        return valor_reserva;
    }

    public void setValor_reserva(int valor_reserva) {
        this.valor_reserva = valor_reserva;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: "+ getId() + "\n" +
                "Email: "+ getEmail() + "\n" +
                "Celular: " + getCelular()+ "\n" +
                "Fecha Prestamo: " + getFecha_pestramo()+ "\n" +
                "Fecha Entrega: " + getFecha_entrega() + "\n" +
                "Valor Reserva: " + getValor_reserva() + "\n" +
                "Placa: " + getPlaca() ;
    }
}
