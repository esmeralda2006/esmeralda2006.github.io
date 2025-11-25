package com.example.appascensor;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout izquierdo;

    private EditText editText;
    private TextView pisoActualTV;
    private TextView direccionTV;
    private int numPisos=0;
    private int pisoActual=0;
    private boolean subiendo=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        izquierdo=findViewById(R.id.izquierdo);
        pisoActualTV=findViewById(R.id.PisoActual);
        direccionTV=findViewById(R.id.Direccion);
        editText=findViewById(R.id.EditText);


        }

    public void crear(View view) {
        String input = editText.getText().toString();
        if (input.isEmpty()) return;

        numPisos = Integer.parseInt(input);
        //limpiar layout

        izquierdo.removeAllViews();

        for (int i = numPisos; i >= 1; i--) {
            TextView piso = new TextView(this);
            piso.setText("Piso " + i);
            piso.setPadding(20, 20, 20, 20);
            piso.setTextSize(18);
            izquierdo.addView(piso);
        }
    }
    // Método para actualizar la información del ascensor
    private void actualizarDerecho() {
        pisoActualTV.setText("Piso actual: " + pisoActual);
        direccionTV.setText("Dirección: " + (subiendo ? "Subiendo" : "Bajando"));
    }

    public void moverAscensor(View view){
        if(subiendo){
            if(pisoActual<numPisos) {
                pisoActual++;
            }else{
                subiendo=false;
                pisoActual--;
            }
        }else{
            if(pisoActual>1) {
                pisoActual--;
            }else{
                subiendo = true;
                pisoActual++;

            }
        }
        actualizarDerecho();

    }

}