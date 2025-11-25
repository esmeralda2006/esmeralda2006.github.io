package com.example.listviewcrsitina;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Persona> {

    private Activity context;
    private ArrayList<Persona> datos;

    public Adaptador(Activity context, ArrayList<Persona> datos) {
        super(context, R.layout.activity_adaptador, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.activity_adaptador, parent, false);
        }

        TextView lblNombre = item.findViewById(R.id.tvNombre);
        TextView lblApellido = item.findViewById(R.id.tvApellidos);

        Persona p = datos.get(position);

        lblNombre.setText(p.getNombre());
        lblApellido.setText(p.getApellidos());

        return item;
    }
}
