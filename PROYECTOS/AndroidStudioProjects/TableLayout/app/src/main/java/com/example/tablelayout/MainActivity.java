package com.example.tablelayout; // 👈 cambia al nombre real de tu paquete

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editUsuario, editPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // conecta con  XML

        // Vincular con los IDs del XML
        editUsuario = findViewById(R.id.editText2);
        editPassword = findViewById(R.id.editText1);
        btnLogin = findViewById(R.id.button1);

        // Acción al pulsar el botón
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editUsuario.getText().toString().trim();
                String password = editPassword.getText().toString();

                if (usuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introduce usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Ejemplo de validación local (sólo de demostración)
                if (usuario.equals("Fatima Valle") && password.equals("12345")) {
                    Toast.makeText(MainActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
            }
}