package com.example.ascensor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout izquierdo;
    private EditText editText;
    private TextView pisoActualTV;
    private TextView direccionTV;
    private int numPisos = 0;
    private int pisoActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        izquierdo = findViewById(R.id.izquierdo);
        pisoActualTV = findViewById(R.id.PisoActual);
        direccionTV = findViewById(R.id.Direccion);
        editText = findViewById(R.id.EditText);
    }

    public void crear(View view) {
        String input = editText.getText().toString();
        if (input.isEmpty()) return;

        numPisos = Integer.parseInt(input);
        if (numPisos <= 0||numPisos>10) {
            Toast.makeText(this, "Ingresa un número válido de pisos", Toast.LENGTH_SHORT).show();
            return;
        }

        pisoActual = 1;
        izquierdo.removeAllViews();

        for (int i = numPisos; i >= 0; i--) {
            Button botonPiso = new Button(this);
            botonPiso.setText("piso " + i);
            final int destino = i;
            botonPiso.setOnClickListener(v -> moverAscensor(destino));
            izquierdo.addView(botonPiso);
        }

        actualizarDerecho();
    }

    private void moverAscensor(int destino) {
        if (destino == pisoActual) return;

        boolean subiendo = destino > pisoActual;
        pisoActual = destino;

        pisoActualTV.setText("Piso actual: " + pisoActual);
        direccionTV.setText("Dirección: " + (subiendo ? "Subiendo" : "Bajando"));
    }

    private void actualizarDerecho() {
        pisoActualTV.setText("Piso actual: " + pisoActual);
        direccionTV.setText("Dirección: -");
    }
}
