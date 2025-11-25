package com.example.checkpracticakotlin;

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var etNombre: EditText? = null
    var etEdad: EditText? = null
    var rgSexo: RadioGroup? = null
    var rbHombre: RadioButton? = null
    var rbMujer: RadioButton? = null
    var btnGuardar: Button? = null
    var tvCantidadDeportes: TextView? = null
    var layoutDeportes: LinearLayout? = null
    var listaCheckBoxes: ArrayList<CheckBox> = ArrayList<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias
        etNombre = findViewById<EditText?>(R.id.etNombre)
        etEdad = findViewById<EditText?>(R.id.etEdad)
        rgSexo = findViewById<RadioGroup?>(R.id.rgSexo)
        rbHombre = findViewById<RadioButton?>(R.id.rbHombre)
        rbMujer = findViewById<RadioButton?>(R.id.rbMujer)
        btnGuardar = findViewById<Button?>(R.id.btnGuardar)
        tvCantidadDeportes = findViewById<TextView?>(R.id.tvCantidadDeportes)
        layoutDeportes = findViewById<LinearLayout?>(R.id.layoutDeportes)

        // Cargar dinámicamente los CheckBox
        cargarDeportes()

        // Acción del botón
        btnGuardar!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                guardarDatos()
            }
        })
    }

    private fun cargarDeportes() {
        val deportes = getResources().getStringArray(R.array.lista_deportes)

        for (deporte in deportes) {
            val cb = CheckBox(this)
            cb.setText(deporte)
            layoutDeportes!!.addView(cb)
            listaCheckBoxes.add(cb)
        }
    }

    private fun guardarDatos() {
        val nombre = etNombre!!.getText().toString().trim { it <= ' ' }
        val edad = etEdad!!.getText().toString().trim { it <= ' ' }

        if (nombre.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        var sexo = ""
        val selectedId = rgSexo!!.getCheckedRadioButtonId()
        if (selectedId == rbHombre!!.getId()) sexo = "Hombre"
        else if (selectedId == rbMujer!!.getId()) sexo = "Mujer"
        else sexo = "No especificado"

        val deportesSeleccionados = StringBuilder()
        var cantidad = 0
        for (cb in listaCheckBoxes) {
            if (cb.isChecked()) {
                deportesSeleccionados.append(cb.getText()).append(" ")
                cantidad++
            }
        }

        val mensaje = "Nombre: " + nombre +
                "\nSexo: " + sexo +
                "\nEdad: " + edad +
                "\nDeportes: " + deportesSeleccionados

        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        tvCantidadDeportes!!.setText("Cantidad de deportes: " + cantidad)
    }
}