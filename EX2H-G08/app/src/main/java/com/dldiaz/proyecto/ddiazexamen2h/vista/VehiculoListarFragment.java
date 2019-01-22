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
import com.dldiaz.proyecto.ddiazexamen2h.controlador.VehiculoImpl;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class VehiculoListarFragment extends Fragment{
    private VehiculoImpl vehiculoImpl;
    List<Vehiculo>vehiculos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.vehiculo_listar, container, false);
        ListView listView = (ListView)v.findViewById(R.id.list);
        vehiculoImpl = new VehiculoImpl(this.getContext());
        vehiculoImpl.open();

        vehiculos = vehiculoImpl.listar();
        vehiculoImpl.close();
        ArrayAdapter<Vehiculo>adapter=new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1,vehiculos);
       // setListAdapter(adapter);
        listView.setAdapter(adapter);
        return v;
    }
    }
