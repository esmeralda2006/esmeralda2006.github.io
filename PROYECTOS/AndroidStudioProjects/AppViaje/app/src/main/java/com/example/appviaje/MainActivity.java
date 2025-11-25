package com.example.appviaje;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgTransporte, rgHotel, rgComida, rgOcio;
    EditText personasTransporte, personasHotel, personasComida, personasOcio;
    TextView calculoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Enlazar vistas
        rgTransporte = findViewById(R.id.rgTransporte);
        rgHotel = findViewById(R.id.rgHotel);
        rgComida = findViewById(R.id.rgComida);
        rgOcio = findViewById(R.id.rgOcio);

        personasTransporte = findViewById(R.id.personasTransporte);
        personasHotel = findViewById(R.id.personasHotel);
        personasComida = findViewById(R.id.personasComida);
        personasOcio = findViewById(R.id.personasOcio);

        calculoTotal = findViewById(R.id.calculoTotal);

        // Detectar cambios en opciones o número de personas
        rgTransporte.setOnCheckedChangeListener((g, id) -> calcularPrecio());
        rgHotel.setOnCheckedChangeListener((g, id) -> calcularPrecio());
        rgComida.setOnCheckedChangeListener((g, id) -> calcularPrecio());
        rgOcio.setOnCheckedChangeListener((g, id) -> calcularPrecio());

        // Cada vez que se escribe en un campo, recalcular
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularPrecio();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        personasTransporte.addTextChangedListener(watcher);
        personasHotel.addTextChangedListener(watcher);
        personasComida.addTextChangedListener(watcher);
        personasOcio.addTextChangedListener(watcher);
    }

    // Calcula el total
    private void calcularPrecio() {
        int total = 0;
        total += calcularCategoria(rgTransporte, personasTransporte);
        total += calcularCategoria(rgHotel, personasHotel);
        total += calcularCategoria(rgComida, personasComida);
        total += calcularCategoria(rgOcio, personasOcio);

        calculoTotal.setText("Precio = " + total + " €");
    }

    // Calcula el precio por categoría
    private int calcularCategoria(RadioGroup grupo, EditText personasInput) {
        int precio = 0;
        int personas = 0;

        try {
            personas = Integer.parseInt(personasInput.getText().toString());
        } catch (NumberFormatException e) {
            personas = 0;
        }

        int checkedId = grupo.getCheckedRadioButtonId();
        if (checkedId != -1) {
            RadioButton opcion = findViewById(checkedId);
            String texto = opcion.getText().toString();

            if (texto.contains("-")) {
                String[] partes = texto.split("-");
                try {
                    precio = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException ignored) {}
            }
        }

        return precio * personas;
    }
}
