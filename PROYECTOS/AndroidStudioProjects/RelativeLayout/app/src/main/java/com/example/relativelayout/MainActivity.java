package com.example.relativelayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editNombre;
    private Button btnAceptar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Enlaza con tu XML

        // Referencias a los elementos del layout
        editNombre = findViewById(R.id.edit01);
        btnAceptar = findViewById(R.id.aceptar);
        btnCancelar = findViewById(R.id.cancelar);

        // Acción del botón Aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombre.getText().toString().trim();

                if (!nombre.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hola " + nombre, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.introduce_nombre), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acción del botón Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNombre.setText(""); // Limpia el campo
                Toast.makeText(MainActivity.this, R.string.campo_borrado, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
