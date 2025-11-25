package com.example.ascensor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layoutPisos;
    private TextView tvPiso, tvDireccion;
    private ArrayList<Button> listaBotones = new ArrayList<>();
    private int pisoActual = 1;   // piso inicial
    private int pisoAnterior = 1; // para calcular dirección

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutPisos = findViewById(R.id.layoutPisos);
        tvPiso = findViewById(R.id.tvPiso);
        tvDireccion = findViewById(R.id.tvDireccion);

        generarBotones(4); // tablero con 4 pisos
        actualizarTablero(); // estado inicial
    }

    private void generarBotones(int numPisos) {
        layoutPisos.removeAllViews();
        listaBotones.clear();

        for (int i = numPisos; i >= 1; i--) {
            Button boton = new Button(this);
            boton.setText(String.valueOf(i));
            boton.setAllCaps(false);

            int piso = i;
            boton.setOnClickListener(v -> moverAscensor(piso));

            layoutPisos.addView(boton);
            listaBotones.add(boton);
        }
    }

    private void moverAscensor(int pisoDestino) {
        pisoAnterior = pisoActual;   // guardamos el anterior
        pisoActual = pisoDestino;    // actualizamos al nuevo
        actualizarTablero();
    }

    private void actualizarTablero() {
        tvPiso.setText(String.valueOf(pisoActual));

        if (pisoActual > pisoAnterior) {
            tvDireccion.setText("sube");
        } else if (pisoActual < pisoAnterior) {
            tvDireccion.setText("baja");
        } else {
            tvDireccion.setText("quieto");
        }
    }
}
