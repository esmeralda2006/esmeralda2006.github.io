package com.example.linearlayoutexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn01, btn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los botones
        btn01 = findViewById(R.id.button01);
        btn02 = findViewById(R.id.button02);

        // Acción del botón 1
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Pulsaste Button01", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del botón 2
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Pulsaste Button02", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
