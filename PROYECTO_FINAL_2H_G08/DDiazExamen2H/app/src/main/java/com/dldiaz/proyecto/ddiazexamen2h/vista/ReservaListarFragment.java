package com.dldiaz.proyecto.ddiazexamen2h.vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.ReservaImpl;
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Reserva;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;

import java.util.List;

public class ReservaListarFragment extends Fragment {
    private ReservaImpl reservaImpl;
    List<Reserva> reservas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.reserva_listar, container, false);
        ListView listView = (ListView)v.findViewById(R.id.listarRerserva);
        reservaImpl = new ReservaImpl(this.getContext());
        reservaImpl.open();
        reservas = reservaImpl.listarR();
        reservaImpl.close();
        ArrayAdapter<Reserva> adapter=new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1,reservas);
        // setListAdapter(adapter);
        listView.setAdapter(adapter);
        return v;
    }
}
