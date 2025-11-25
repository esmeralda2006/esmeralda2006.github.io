package com.example.listviewmario;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<ArrayList<String>> {

    private Activity context;
    private ArrayList<ArrayList<String>> datos;

    public Adaptador(Activity context, ArrayList<ArrayList<String>> datos) {
        super(context, R.layout.listview_bloc, datos);
        this.context = context;
        this.datos = datos;
    }

    static class ViewHolder {
        TextView lblNombre;
        TextView lblValorEner;
        TextView lblCantidad;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listview_bloc, parent, false);

            holder = new ViewHolder();
            holder.lblNombre = convertView.findViewById(R.id.lbl_nombre);
            holder.lblValorEner = convertView.findViewById(R.id.lbl_valorEner);
            holder.lblCantidad = convertView.findViewById(R.id.lbl_cantidad);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArrayList<String> fila = datos.get(position);

        holder.lblNombre.setText(fila.get(0));
        holder.lblValorEner.setText(fila.get(1));
        holder.lblCantidad.setText("0");

        return convertView;
    }
}
