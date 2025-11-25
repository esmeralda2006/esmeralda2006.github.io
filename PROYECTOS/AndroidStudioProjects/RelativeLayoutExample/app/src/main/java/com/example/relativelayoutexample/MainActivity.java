package com.example.relativelayoutexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editFirstName, editSecondName;
    private Button buttonSubmit, buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los componentes
        editFirstName = findViewById(R.id.editFirstName);
        editSecondName = findViewById(R.id.editSecondName);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonReset = findViewById(R.id.buttonReset);

        // Acción del botón Submit
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editFirstName.getText().toString().trim();
                String secondName = editSecondName.getText().toString().trim();

                if (firstName.isEmpty() || secondName.isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_fill_both), Toast.LENGTH_SHORT).show();
                } else {
                    String message = getString(R.string.first_name_label) + " " + firstName
                            + "\n" + getString(R.string.second_name_label) + " " + secondName;
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Acción del botón Reset
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFirstName.setText("");
                editSecondName.setText("");
            }
        });
    }
}
