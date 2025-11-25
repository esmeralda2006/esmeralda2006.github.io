package com.example.checkpractica;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etEdad;
    RadioGroup rgSexo;
    RadioButton rbHombre, rbMujer;
    Button btnGuardar;
    TextView tvCantidadDeportes;
    LinearLayout layoutDeportes;
    ArrayList<CheckBox> listaCheckBoxes = new ArrayList<>();
    int maxDeportes = 0;
    String sexoSeleccionado = "";

    String[] deportesHombre = {"Tenis", "Padel", "Futbol","Futbol Sala","Karate"};
    String[] deportesMujer = {"Danza", "Cocina", "Yoga", "Vóley","Ballet","Danza Moderna"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referencias al xml
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        rgSexo = findViewById(R.id.rgSexo);
        rbHombre = findViewById(R.id.rbHombre);
        rbMujer = findViewById(R.id.rbMujer);
        btnGuardar = findViewById(R.id.btnGuardar);
        tvCantidadDeportes = findViewById(R.id.tvCantidadDeportes);
        layoutDeportes = findViewById(R.id.layoutDeportes);

        // segun sexo elegido llama al array correspondiente
        rgSexo.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == rbHombre.getId()) {
                sexoSeleccionado = "Hombre";
                cargarDeportes(deportesHombre, 3);
            } else if (checkedId == rbMujer.getId()) {
                sexoSeleccionado = "Mujer";
                cargarDeportes(deportesMujer, 4);
            }
        });


        btnGuardar.setOnClickListener(v -> guardarDatos());
    }

    private void cargarDeportes(String[] deportes, int max) {
        layoutDeportes.removeAllViews();
        listaCheckBoxes.clear();
        maxDeportes = max;

        for (String deporte : deportes) {
            CheckBox cb = new CheckBox(this);
            cb.setText(deporte);

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int seleccionados = contarSeleccionados();
                if (seleccionados > maxDeportes) {
                    buttonView.setChecked(false);
                    Toast.makeText(this, "Máximo " + maxDeportes + " deportes", Toast.LENGTH_SHORT).show();
                }
            });

            layoutDeportes.addView(cb);
            listaCheckBoxes.add(cb);
        }
    }

    private int contarSeleccionados() {
        int count = 0;
        for (CheckBox cb : listaCheckBoxes) {
            if (cb.isChecked()) count++;
        }
        return count;
    }

    private void guardarDatos() {
        String nombre = etNombre.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();

        if (nombre.isEmpty() || edad.isEmpty() || sexoSeleccionado.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder deportesSeleccionados = new StringBuilder();
        int cantidad = 0;
        for (CheckBox cb : listaCheckBoxes) {
            if (cb.isChecked()) {
                deportesSeleccionados.append(cb.getText()).append(" ");
                cantidad++;
            }
        }

        String mensaje = "Nombre: " + nombre +
                "\nSexo: " + sexoSeleccionado +
                "\nEdad: " + edad +
                "\nDeportes: " + deportesSeleccionados;

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        tvCantidadDeportes.setText("Cantidad de deportes: " + cantidad);
    }
}

