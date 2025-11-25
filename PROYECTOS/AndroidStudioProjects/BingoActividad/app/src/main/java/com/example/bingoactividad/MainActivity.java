package com.example.bingoactividad;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    Button btnGenerar;
    Spinner spHistorial;
    ListView lvCartonA, lvCartonB;

    ArrayList<Integer> cartonA = new ArrayList<>();
    ArrayList<Integer> cartonB = new ArrayList<>();

    ArrayList<String> textoA = new ArrayList<>();
    ArrayList<String> textoB = new ArrayList<>();

    ArrayList<String> historial = new ArrayList<>();

    ArrayAdapter<String> adapterA;
    ArrayAdapter<String> adapterB;
    ArrayAdapter<String> adapterHist;

    int cantidadCarton = 5;
    int marcadosA = 0;
    int marcadosB = 0;

    boolean juegoTerminado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGenerar = findViewById(R.id.btnGenerar);
        spHistorial = findViewById(R.id.spHistorial);
        lvCartonA = findViewById(R.id.lvCartonA);
        lvCartonB = findViewById(R.id.lvCartonB);

        generarCartones();

        adapterA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textoA);
        adapterB = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textoB);
        adapterHist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, historial);

        adapterHist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lvCartonA.setAdapter(adapterA);
        lvCartonB.setAdapter(adapterB);
        spHistorial.setAdapter(adapterHist);

        btnGenerar.setOnClickListener(v -> generarNumero());
    }
    private void generarCartones() {

        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 1; i <= 20; i++) pool.add(i);

        Collections.shuffle(pool);

        cartonA.clear();
        cartonB.clear();
        textoA.clear();
        textoB.clear();


        for (int i = 0; i < cantidadCarton; i++) {
            cartonA.add(pool.get(i));
            textoA.add(String.valueOf(pool.get(i)));
        }


        for (int i = cantidadCarton; i < cantidadCarton*2; i++) {
            cartonB.add(pool.get(i));
            textoB.add(String.valueOf(pool.get(i)));
        }

        marcadosA = 0;
        marcadosB = 0;
        juegoTerminado = false;
    }
    private void generarNumero() {

        if (juegoTerminado) return;

        int num = (int)(Math.random()*20) + 1;

        historial.add(String.valueOf(num));
        adapterHist.notifyDataSetChanged();

        for (int i = 0; i < cartonA.size(); i++) {
            if (cartonA.get(i) == num && !textoA.get(i).contains("X")) {
                textoA.set(i, num + " X");
                marcadosA++;
                adapterA.notifyDataSetChanged();
            }
        }

        for (int i = 0; i < cartonB.size(); i++) {
            if (cartonB.get(i) == num && !textoB.get(i).contains("X")) {
                textoB.set(i, num + " X");
                marcadosB++;
                adapterB.notifyDataSetChanged();
            }
        }

        if (marcadosA == cantidadCarton && marcadosB == cantidadCarton) {
            Toast.makeText(this, "¡Empate! Ambos hicieron BINGO", Toast.LENGTH_LONG).show();
            juegoTerminado = true;
        }
        else if (marcadosA == cantidadCarton) {
            Toast.makeText(this, "¡Cartón A ha ganado!", Toast.LENGTH_LONG).show();
            juegoTerminado = true;
        }
        else if (marcadosB == cantidadCarton) {
            Toast.makeText(this, "¡Cartón B ha ganado!", Toast.LENGTH_LONG).show();
            juegoTerminado = true;
        }
    }
}
