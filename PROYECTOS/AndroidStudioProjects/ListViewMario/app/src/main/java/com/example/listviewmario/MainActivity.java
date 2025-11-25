package com.example.listviewmario;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<ArrayList<String>> array;
    private Adaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        array = recogerDatos();

        adapter = new Adaptador(this, array);

        ListView lv = findViewById(R.id.lv_1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
    }

    public ArrayList<ArrayList<String>> recogerDatos() {

        TypedArray ta = getResources().obtainTypedArray(R.array.array_maestro);
        ArrayList<ArrayList<String>> arrayB = new ArrayList<>();

        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);

            if (id != 0) {
                arrayB.add(new ArrayList<>(Arrays.asList(getResources()
                        .getStringArray(id))));
            }
        }

        ta.recycle();
        return arrayB;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView tv = view.findViewById(R.id.lbl_cantidad);

        int valor = Integer.parseInt(tv.getText().toString());
        valor++;

        tv.setText(String.valueOf(valor));
    }

    @Override
    public void onClick(View v) {

        TextView nombre = findViewById(R.id.etxt_nombre);
        TextView valorEner = findViewById(R.id.etxt_valorEner);

        if (!nombre.getText().toString().isEmpty() &&
                !valorEner.getText().toString().isEmpty()) {

            ArrayList<String> nuevaFila = new ArrayList<>();

            nuevaFila.add(nombre.getText().toString());
            nuevaFila.add(valorEner.getText().toString() + "Kcal");

            array.add(nuevaFila);

            adapter.notifyDataSetChanged();
        }
    }
}
