package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgBebidas;
    private Spinner spinnerEuros;
    private MultiSelectSpinner spinnerCentimos;
    private TextView lblResultado;

    private String[] listaBebidas;
    private ArrayList<Integer> listaEuros;
    private ArrayList<String> listaCentimosString;

    private ArrayList<Integer> seleccionCentimosActual = new ArrayList<>();
    private ArrayList<Integer> usadosEuros = new ArrayList<>();
    private ArrayList<Integer> usadosCent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgBebidas = findViewById(R.id.rgBebidas);
        spinnerEuros = findViewById(R.id.spinnerEuros);
        spinnerCentimos = findViewById(R.id.spinnerCentimosMulti);
        lblResultado = findViewById(R.id.lblResultado);

        listaBebidas = getResources().getStringArray(R.array.bebidas);

        for (String b : listaBebidas) {
            String nombre = b.split("-")[0].trim();
            RadioButton rb = new RadioButton(this);
            rb.setText(nombre);
            rgBebidas.addView(rb);
        }

        if (rgBebidas.getChildCount() > 0)
            ((RadioButton) rgBebidas.getChildAt(0)).setChecked(true);

        String[] eurosString = getResources().getStringArray(R.array.lista_euros);
        String[] centString = getResources().getStringArray(R.array.lista_centimos);

        listaEuros = new ArrayList<>();
        for (String s : eurosString) listaEuros.add(Integer.parseInt(s));

        listaCentimosString = new ArrayList<>();
        for (String s : centString) listaCentimosString.add(s);

        ArrayAdapter<Integer> adapterE = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                listaEuros
        );
        spinnerEuros.setAdapter(adapterE);

        spinnerCentimos.setItems(listaCentimosString, valores -> {
            seleccionCentimosActual = valores;
            verificar();
        });

        verificar();
    }

    private void eliminarCorrectos() {
        int e = (int) spinnerEuros.getSelectedItem();

        if (e != 0 && !usadosEuros.contains(e)) {
            usadosEuros.add(e);
            listaEuros.remove((Integer) e);

            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    listaEuros
            );
            spinnerEuros.setAdapter(adapter);
        }

        for (int c : seleccionCentimosActual) {
            if (!usadosCent.contains(c)) {
                usadosCent.add(c);
                listaCentimosString.remove(String.valueOf(c));
            }
        }

        spinnerCentimos.setItems(listaCentimosString, valores -> {
            seleccionCentimosActual = valores;
            verificar();
        });
    }

    private void verificar() {
        int id = rgBebidas.getCheckedRadioButtonId();
        RadioButton rb = findViewById(id);
        String seleccion = rb.getText().toString();

        int euros = (int) spinnerEuros.getSelectedItem();

        int sumaCent = 0;
        for (int c : seleccionCentimosActual) sumaCent += c;

        for (String bebida : listaBebidas) {
            String[] p = bebida.split("-");
            String nombre = p[0].trim();
            int pe = Integer.parseInt(p[1].trim());
            int pc = Integer.parseInt(p[2].trim());

            if (nombre.equals(seleccion)) {

                if (pe == euros && pc == sumaCent) {
                    lblResultado.setText("Correcto");
                    lblResultado.setTextColor(Color.parseColor("#43A047"));
                    eliminarCorrectos();
                } else {
                    lblResultado.setText("Incorrecto");
                    lblResultado.setTextColor(Color.RED);
                }

                break;
            }
        }
    }
}
