package com.example.maquinaexpendedora;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgBebidas;
    private Spinner spinnerEuros, spinnerCentimos;
    private TextView lblResultado;
    private String[] listaBebidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgBebidas = findViewById(R.id.rgBebidas);
        spinnerEuros = findViewById(R.id.spinnerEuros);
        spinnerCentimos = findViewById(R.id.spinnerCentimos);
        lblResultado = findViewById(R.id.lblResultado);

        listaBebidas = getResources().getStringArray(R.array.bebidas);


        for(String bebida : listaBebidas){
            String nombre = bebida.split("-")[0].trim();
            RadioButton rb = new RadioButton(this);
            rb.setText(nombre);
            rgBebidas.addView(rb);
        }


        if(rgBebidas.getChildCount() > 0){
            ((RadioButton) rgBebidas.getChildAt(0)).setChecked(true);
        }


        Integer[] euros = {0,1,2,3,4,5,6,7,8,9,10};
        Integer[] centimos = {0,10,20,30,40,50,60,70,80,90};

        ArrayAdapter<Integer> adapterEuros = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, euros);
        adapterEuros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEuros.setAdapter(adapterEuros);

        ArrayAdapter<Integer> adapterCentimos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, centimos);
        adapterCentimos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentimos.setAdapter(adapterCentimos);

        rgBebidas.setOnCheckedChangeListener((group, checkedId) -> verificar());

        spinnerEuros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                verificar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerCentimos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                verificar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        verificar();
    }

    private void verificar(){
        int checkedId = rgBebidas.getCheckedRadioButtonId();
        RadioButton rb = findViewById(checkedId);
        String seleccion = rb.getText().toString();

        int euros = (int) spinnerEuros.getSelectedItem();
        int centimos = (int) spinnerCentimos.getSelectedItem();

        for(String bebida : listaBebidas){
            String[] partes = bebida.split("-");
            String nombre = partes[0].trim();
            int precioEuros = Integer.parseInt(partes[1].trim());
            int precioCentimos = Integer.parseInt(partes[2].trim());

            if(nombre.equals(seleccion)){
                if(precioEuros == euros && precioCentimos == centimos){
                    lblResultado.setText("Correcto");
                    lblResultado.setTextColor(Color.parseColor("#43A047"));
                } else {
                    lblResultado.setText("Incorrecto");
                    lblResultado.setTextColor(Color.RED);
                }
                break;
            }
        }
    }
}
