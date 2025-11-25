package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MultiSelectSpinner extends androidx.appcompat.widget.AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {

    private ArrayList<String> items = new ArrayList<>();
    private boolean[] seleccionados;
    private MultiSelectListener listener;

    public interface MultiSelectListener {
        void onItemsSelected(ArrayList<Integer> valores);
    }

    public MultiSelectSpinner(Context context) {
        super(context);
    }

    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setItems(ArrayList<String> items, MultiSelectListener listener) {
        this.items = items;
        this.listener = listener;
        this.seleccionados = new boolean[items.size()];

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Seleccionar"}
        );
        setAdapter(adapter);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccionar céntimos");

        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[0]),
                seleccionados,
                this
        );

        builder.setPositiveButton("OK", (dialog, which) -> {
            ArrayList<Integer> lista = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                if (seleccionados[i]) lista.add(Integer.parseInt(items.get(i)));
            }
            if (listener != null) listener.onItemsSelected(lista);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int index, boolean isChecked) {
        seleccionados[index] = isChecked;
    }
}
