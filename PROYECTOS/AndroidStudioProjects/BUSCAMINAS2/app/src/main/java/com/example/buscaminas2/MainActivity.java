package com.example.buscaminas2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.graphics.Color;
import android.widget.GridLayout;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText etNumero;
    Button btnReiniciar;
    GridLayout gridLayout;
    TextView tvInfo;
    CheckBox MostrarMinas;

    int totalBotones = 0, minas = 0, fallos = 0, maxFallos = 0, minasEncontradas = 0;
    ArrayList<Integer> listaMinas = new ArrayList<>();
    boolean juegoTerminado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumero = findViewById(R.id.etNumero);
        gridLayout = findViewById(R.id.gridLayout);
        tvInfo = findViewById(R.id.tvInfo);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        MostrarMinas = findViewById(R.id.MostrarMinas);

        etNumero.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                crearJuego();
                return true;
            }
            return false;
        });

        MostrarMinas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mostrarTodasLasMinas();
            } else {
                ocultarMinas();
            }
        });

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
        maxFallos = totalBotones / 2;

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

        if (fallos >= maxFallos) {
            Toast.makeText(this, "¡Has perdido!", Toast.LENGTH_SHORT).show();
            juegoTerminado = true;
            bloquearTodosLosBotones();
        }

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
        MostrarMinas.setChecked(false);
    }

    private void mostrarTodasLasMinas() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button b = (Button) gridLayout.getChildAt(i);
            try {
                int num = Integer.parseInt(b.getText().toString());
                if (listaMinas.contains(num)) {
                    b.setBackgroundColor(Color.RED);
                    b.setEnabled(false);
                    minasEncontradas++;
                    actualizarTexto();
                    Toast.makeText(this, "Se ha marcado una mina automáticamente", Toast.LENGTH_SHORT).show();
                    break;



                }
            } catch (NumberFormatException ignored) {}
        }
    }

    private void ocultarMinas() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button b = (Button) gridLayout.getChildAt(i);
            try {
                int num = Integer.parseInt(b.getText().toString());
                if (listaMinas.contains(num) && b.isEnabled()) {
                    b.setBackgroundColor(Color.LTGRAY);
                    b.setText(String.valueOf(num));
                }
            } catch (NumberFormatException ignored) {}
        }
    }
}