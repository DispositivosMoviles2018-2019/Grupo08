package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;

public class BuscarReservaFragment extends Fragment {
    private VehiculoImpl vehiculoImpl;
    EditText placa;
    TextView info;
    Button buscarPorPlaca;
    VehiculoImpl vehiculoData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buscar_reserva_fragment, container, false);
        placa = (EditText) v.findViewById(R.id.ingresePlaca);
        info= (TextView) v.findViewById(R.id.infoPlaca);
        buscarPorPlaca= (Button) v.findViewById(R.id.consultarPorPlaca);
        System.out.println("placa a buscar:" + placa.getText().toString());
        //vehiculoImpl = new VehiculoImpl(this.getContext());
        //vehiculoImpl.open();
      //  vehiculoData.getVehiculo(placa.getText().toString());
       // info.setText((CharSequence) vehiculoData);
        //vehiculoImpl.close();
        return v;
    }
    public void buscarPorPlaca(){

    }
}
