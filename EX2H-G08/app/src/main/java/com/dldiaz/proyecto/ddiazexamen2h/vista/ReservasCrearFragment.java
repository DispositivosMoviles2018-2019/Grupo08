package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.ReservaImpl;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Reserva;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;
import com.dldiaz.proyecto.ddiazexamen2h.utilidades.SendMailTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReservasCrearFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    Vehiculo vehiculo;
    Reserva reserva;
    ReservaImpl reservaData;
    VehiculoImpl vehiculoData;
    Button buttonSend,reservar;
    EditText emailTo, phoneNo, placaReserva;
    String phone, message, plac;
    CalendarView fecPrestamo,fecEntrega;
    TextView dateDisplayPrestamo, dateDisplayEntrega,valorReserva;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reservar_crear, container, false);
        buttonSend = (Button) v.findViewById(R.id.buttonSend);
        emailTo = (EditText) v.findViewById(R.id.email);
        phoneNo = (EditText) v.findViewById(R.id.celular);
        placaReserva = (EditText) v.findViewById(R.id.placaReserva);
        fecPrestamo = (CalendarView) v.findViewById(R.id.fechaPrestamo);
       dateDisplayPrestamo = (TextView) v.findViewById(R.id.displayFechaPrestamo);
        fecEntrega = (CalendarView) v.findViewById(R.id.fechaEntrega);
        dateDisplayEntrega = (TextView) v.findViewById(R.id.displayFechaEntrega);
        valorReserva = (TextView) v.findViewById(R.id.valorReserva);
        guardarFecha();
        vehiculoData = new VehiculoImpl(this.getContext());
        vehiculoData.open();
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
                enviarCorre();

            }
        });
        reservar = (Button) v.findViewById(R.id.btnReservar);
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    insertarReserva();
            }


        });
        reservaData = new ReservaImpl(this.getContext());
        reservaData.open();
        return v;

    }
    public void guardarFecha(){
        fecPrestamo.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i2, int i3) {
                dateDisplayPrestamo.setText(i3 + "/" + i2 + 1 + "/" + i);
            }
        });
        fecEntrega.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                dateDisplayEntrega.setText(i2 + "/" + i1 + 1 + "/" + i);
            }
        });
    }
    private void enviarCorre(){
        Log.i("SendMailActivity", "Send Button Clicked.");
        String toEmails = emailTo.getText().toString();
        List<String> toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + emailTo.getText().toString());
        String mensaje = "Confirmacion de reserva del vehiculo con placa " + placaReserva.getText().toString();
        //Correo, contrasenia, correo al que envia, asunto del mensaje y el mensaje
        new SendMailTask(this.getActivity()).execute("dayanalizeth.1994@gmail.com",
                "Lize++52uc", toEmailList, "Reserva Vehiculo", obtenerDatosReserva());
    }
    public Reserva obtenerDatosReserva(){
        reserva = new Reserva();
        reserva = reservaData.getReserva(placaReserva.getText().toString());
        return  reserva;

    }
    private void insertarReserva() {
        reserva = new Reserva();
        vehiculo = new Vehiculo();
        reserva.setEmail(emailTo.getText().toString());
        reserva.setCelular(phoneNo.getText().toString());
        reserva.setFecha_pestramo(dateDisplayPrestamo.getText().toString());
        reserva.setFecha_entrega(dateDisplayEntrega.getText().toString());
        reserva.setPlaca(placaReserva.getText().toString());

        vehiculo = vehiculoData.getVehiculo(placaReserva.getText().toString());
        if (vehiculo.isEstado() == true) {
            Toast t = Toast.makeText(this.getContext(), "El vehiculo ya esta reservado", Toast.LENGTH_SHORT);
            t.show();
        } else {
            Date dateP = null;
            try {
                dateP = new SimpleDateFormat("dd/MM/yyyy").parse(dateDisplayPrestamo.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dateE = null;
            try {
                dateE = new SimpleDateFormat("dd/MM/yyyy").parse(dateDisplayEntrega.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int dias = (int) ((dateE.getTime() - dateP.getTime()) / 86400000);
            System.out.println("DAYYYYYYYYYYYYYYYY" + dias);
            if (vehiculo.getTipo().equalsIgnoreCase("Automovil")) {
                reserva.setValor_reserva(dias * 60);
            }
            if (vehiculo.getTipo().equalsIgnoreCase("Camioneta")) {
                reserva.setValor_reserva(dias * 75);
            }
            if (vehiculo.getTipo().equalsIgnoreCase("Furgoneta")) {
                reserva.setValor_reserva(dias * 100);
            }
            vehiculo.setEstado(true);
            vehiculoData.actualizar(vehiculo);
            reservaData.crear(reserva);
            Toast t = Toast.makeText(this.getContext(), "El vehiculo se ha reservado correctamente", Toast.LENGTH_SHORT);
            t.show();
        }
        obtenerDatosReserva();
    }

    protected void sendSMSMessage() {
        phone = phoneNo.getText().toString();
        message = "Confirmacion reserva auto";

        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, message, null, null);
                    Toast.makeText(this.getContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}
