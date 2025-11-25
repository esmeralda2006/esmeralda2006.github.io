package com.example.buscaminas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText etNumero;
    Button btnCrear, btnReiniciar;
    GridLayout gridLayout;
    TextView tvInfo;
    int totalBotones = 0, minas = 0, fallos = 0, maxFallos = 0, minasEncontradas = 0;
    ArrayList<Integer> listaMinas = new ArrayList<>();
    boolean juegoTerminado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumero = findViewById(R.id.etNumero);
        btnCrear = findViewById(R.id.btnCrear);
        gridLayout = findViewById(R.id.gridLayout);
        tvInfo = findViewById(R.id.tvInfo);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        btnCrear.setOnClickListener(v -> crearJuego());
        btnReiniciar.setOnClickListener(v -> reiniciar());
    }

    private void crearJuego() {
        String texto = etNumero.getText().toString().trim();
        if (texto.isEmpty()) return;

        totalBotones = Integer.parseInt(texto);
        if (totalBotones < 4 || totalBotones > 30) {
            Toast.makeText(this, "Minimo 4 y máximo 30 botones", Toast.LENGTH_SHORT).show();
            return;
        }

        gridLayout.removeAllViews();
        listaMinas.clear();
        fallos = 0;
        minasEncontradas = 0;
        juegoTerminado = false;

        minas = totalBotones / 4;
        maxFallos = totalBotones / 2; // 50% de los botones

        generarMinas();

        gridLayout.post(new Runnable() {
            @Override
            public void run() {
                configurarGrid();
            }
        });
    }

    private void generarMinas() {
        Random random = new Random();
        while (listaMinas.size() < minas) {
            int n = random.nextInt(totalBotones) + 1;
            if (!listaMinas.contains(n)) listaMinas.add(n);
        }
    }

    private void configurarGrid() {
        int columnas = (int) Math.ceil(Math.sqrt(totalBotones));
        int filas = (int) Math.ceil((double) totalBotones / columnas);
        gridLayout.setColumnCount(columnas);
        gridLayout.setRowCount(filas);

        int anchoGrid = gridLayout.getWidth();
        int altoGrid = gridLayout.getHeight();

        int espacioPorBoton = anchoGrid / columnas - 12;
        int altoPorBoton = altoGrid / filas - 12;

        int buttonSize = Math.min(espacioPorBoton, altoPorBoton);

        for (int i = 1; i <= totalBotones; i++) {
            Button b = new Button(this);
            b.setText(String.valueOf(i));
            b.setBackgroundColor(Color.LTGRAY);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            params.setMargins(6, 6, 6, 6);
            b.setLayoutParams(params);

            int finalI = i;
            b.setOnClickListener(v -> verificar(b, finalI));
            gridLayout.addView(b);
        }

        actualizarTexto();
    }

    private void verificar(Button b, int num) {
        if (juegoTerminado) return;

        if (listaMinas.contains(num)) {
            b.setBackgroundColor(Color.RED);
            b.setText("✔");
            minasEncontradas++;
            Toast.makeText(this, "¡Has encontrado una mina!", Toast.LENGTH_SHORT).show();
        } else {
            b.setBackgroundColor(Color.WHITE);
            b.setEnabled(false);
            fallos++;
        }

        // Revisar fin del juego: pérdida
        if (fallos >= maxFallos) {
            Toast.makeText(this, "¡Has perdido!", Toast.LENGTH_SHORT).show();
            juegoTerminado = true;
            bloquearTodosLosBotones();
        }

        // Revisar fin del juego: victoria
        if (minasEncontradas == minas) {
            Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_SHORT).show();
            juegoTerminado = true;
            bloquearTodosLosBotones();
        }

        actualizarTexto();
    }

    private void bloquearTodosLosBotones() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            v.setEnabled(false);
        }
    }

    private void actualizarTexto() {
        tvInfo.setText("Fallos: " + fallos +
                " | Fallos posibles: " + maxFallos +
                " | Minas: " + minas +
                " | Encontradas: " + minasEncontradas);
    }

    private void reiniciar() {
        gridLayout.removeAllViews();
        etNumero.setText("");
        tvInfo.setText("Fallos: 0 | Fallos posibles: 0 | Minas: 0 |Encontradas: 0");
        listaMinas.clear();
        juegoTerminado = false;
        minasEncontradas = 0;
        fallos = 0;
    }
}