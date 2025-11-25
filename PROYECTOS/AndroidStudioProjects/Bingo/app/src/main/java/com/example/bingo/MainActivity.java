package com.example.bingo;

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
    ListView lvCarton;

    ArrayList<Integer> carton = new ArrayList<>();
    ArrayList<String> cartonTexto = new ArrayList<>();
    ArrayList<String> historial = new ArrayList<>();

    ArrayAdapter<String> adapterCarton;
    ArrayAdapter<String> adapterSpinner;

    int totalMarcados = 0;
    int cantidadCarton = 8; // ← CUÁNTOS NÚMEROS QUIERES EN EL CARTÓN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGenerar = findViewById(R.id.btnGenerar);
        spHistorial = findViewById(R.id.spHistorial);
        lvCarton = findViewById(R.id.lvCarton);

        generarCarton();

        adapterCarton = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, cartonTexto);
        lvCarton.setAdapter(adapterCarton);

        adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, historial);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHistorial.setAdapter(adapterSpinner);

        btnGenerar.setOnClickListener(v -> generarNumero());
    }

    private void generarCarton() {

        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 1; i <= 20; i++) pool.add(i);

        Collections.shuffle(pool);

        carton.clear();
        cartonTexto.clear();


        for (int i = 0; i < cantidadCarton; i++) {
            carton.add(pool.get(i));
            cartonTexto.add(String.valueOf(pool.get(i)));
        }

        totalMarcados = 0;
    }

    private void generarNumero() {

        int num = (int) (Math.random() * 20) + 1;


        historial.add(String.valueOf(num));
        adapterSpinner.notifyDataSetChanged();

        // Ver si está en el cartón
        for (int i = 0; i < carton.size(); i++) {
            if (carton.get(i) == num && !cartonTexto.get(i).contains("X")) {

                cartonTexto.set(i, num + "  X");
                totalMarcados++;

                adapterCarton.notifyDataSetChanged();
            }
        }


        if (totalMarcados == cantidadCarton) {
            Toast.makeText(this, "¡BINGO!", Toast.LENGTH_LONG).show();
            btnGenerar.setEnabled(false);
        }
    }
}
