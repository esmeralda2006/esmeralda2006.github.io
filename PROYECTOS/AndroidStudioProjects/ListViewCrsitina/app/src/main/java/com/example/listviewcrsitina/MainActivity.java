package com.example.listviewcrsitina;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos;
    private Button bGuardar;
    private ListView lista;
    private ArrayList<Persona> listaPersonas;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        bGuardar = findViewById(R.id.bGuardar);
        lista = findViewById(R.id.lista);

        listaPersonas = new ArrayList<>();
        adaptador = new Adaptador(this, listaPersonas);
        lista.setAdapter(adaptador);

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString();
                String apellidos = etApellidos.getText().toString();

                if (!nombre.isEmpty() && !apellidos.isEmpty()) {
                    listaPersonas.add(new Persona(nombre, apellidos));
                    adaptador.notifyDataSetChanged();
                }
            }
        });
    }
}
