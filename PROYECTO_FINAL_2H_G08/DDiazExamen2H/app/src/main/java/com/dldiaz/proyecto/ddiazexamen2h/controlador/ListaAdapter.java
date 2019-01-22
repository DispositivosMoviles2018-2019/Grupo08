package com.dldiaz.proyecto.ddiazexamen2h.controlador;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dldiaz.proyecto.ddiazexamen2h.R;
import com.dldiaz.proyecto.ddiazexamen2h.modelo.Vehiculo;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ListaAdapter extends BaseAdapter {
    protected Activity activity;
    protected List<Vehiculo> vehiculo;
    ListaAdapter adapter;
    public ListaAdapter(Activity activity, List<Vehiculo> vehiculo) {
        super();
        this.activity = activity;
        this.vehiculo = vehiculo;
    }

    @Override
    public int getCount() {
        return vehiculo.size();
    }

    @Override
    public Object getItem(int position) {
        return vehiculo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.vehiculo, null);

        }
        Vehiculo vehi= vehiculo.get(position);
        TextView placa = (TextView) v.findViewById(R.id.placa);
        placa.setText(vehi.getPlaca());
        TextView  marca = (TextView) v.findViewById(R.id.marca);
        marca.setText(vehi.getMarca());
        TextView costo = (TextView) v.findViewById(R.id.costo);
        costo.setText(Double.toString(vehi.getCosto()));
        //TextView color = (TextView) v.findViewById(R.id.color);
       // color.setText(vehi.getColor());
        //CalendarView fechaFabricacion = (CalendarView) v.findViewById(R.id.calendario);
       // TextView dateDisplay = (TextView) v.findViewById(R.id.displayFecha);
        //dateDisplay.setText(vehi.getFecFabricacion());
        TextView matriculado =(TextView) v.findViewById(R.id.matriculado);
        matriculado.setText(Boolean.toString(vehi.isMatriculado()));
        return v;
    }
}
