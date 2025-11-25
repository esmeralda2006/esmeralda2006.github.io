package com.example.kotlinviaje;

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var mainLayout: LinearLayout? = null
    var rgTransporte: RadioGroup? = null
    var rgHotel: RadioGroup? = null
    var rgComida: RadioGroup? = null
    var rgOcio: RadioGroup? = null
    var personasTransporte: EditText? = null
    var personasHotel: EditText? = null
    var personasComida: EditText? = null
    var personasOcio: EditText? = null
    var calculoTotal: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mainLayout = findViewById<LinearLayout?>(R.id.main)

        // Generar los RadioGroup + EditText dinámicamente
        rellenarContenedor()

        //  agregar el cuadro del total
        crearCuadroTotal()
    }

    private fun crearCuadroTotal() {
        calculoTotal = TextView(this)
        calculoTotal!!.setText(getString(R.string.precio_0))
        calculoTotal!!.setTextSize(24f)
        calculoTotal!!.setTextColor(Color.WHITE)
        calculoTotal!!.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER)
        calculoTotal!!.setBackgroundColor(Color.parseColor("#1E88E5"))
        calculoTotal!!.setPadding(40, 30, 40, 30)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 40, 0, 60)
        calculoTotal!!.setLayoutParams(params)

        calculoTotal!!.setElevation(8f)
        calculoTotal!!.setTranslationZ(8f)
        calculoTotal!!.setText("Precio = 0 €")

        mainLayout!!.addView(calculoTotal)
    }

    fun rellenarContenedor() {
        val listaOpciones = getResources().getStringArray(R.array.listaOpciones)

        for (opcion in listaOpciones) {
            // Título
            val label = TextView(this)
            label.setText(opcion)
            label.setTextSize(20f)
            label.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER)
            label.setTextColor(Color.parseColor("#000000"))
            label.setPadding(0, 24, 0, 8)
            mainLayout!!.addView(label)

            // RadioGroup
            val rg = RadioGroup(this)
            rg.setOrientation(RadioGroup.VERTICAL)
            mainLayout!!.addView(rg)

            val arrayId = getResources().getIdentifier(opcion, "array", getPackageName())
            val listaAux = getResources().getStringArray(arrayId)

            for (item in listaAux) {
                val rb = RadioButton(this)
                rb.setText(item)
                rb.setTextSize(16f)
                rg.addView(rb)
                rb.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    if (isChecked) calcularPrecio()
                })
            }

            //  EditText debajo de cada radio button
            val et = EditText(this)
            et.setHint("Personas " + opcion)
            et.setInputType(InputType.TYPE_CLASS_NUMBER)
            et.setBackgroundColor(Color.parseColor("#EEEEEE"))
            et.setPadding(24, 12, 24, 12)
            et.setTextSize(16f)

            val etParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            etParams.setMargins(0, 8, 0, 16)
            et.setLayoutParams(etParams)

            mainLayout!!.addView(et)

            et.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    calcularPrecio()
                }

                override fun afterTextChanged(s: Editable?) {}
            })


            when (opcion) {
                "Transporte" -> {
                    rgTransporte = rg
                    personasTransporte = et
                }

                "Hotel" -> {
                    rgHotel = rg
                    personasHotel = et
                }

                "Comida" -> {
                    rgComida = rg
                    personasComida = et
                }

                "Ocio" -> {
                    rgOcio = rg
                    personasOcio = et
                }
            }
        }
    }

    private fun calcularPrecio() {
        var total = 0
        total += calcularCategoria(rgTransporte, personasTransporte)
        total += calcularCategoria(rgHotel, personasHotel)
        total += calcularCategoria(rgComida, personasComida)
        total += calcularCategoria(rgOcio, personasOcio)

        // Cambiar color si el total es > 0
        if (total > 0) {
            calculoTotal!!.setBackgroundColor(Color.parseColor("#43A047")) // verde
        } else {
            calculoTotal!!.setBackgroundColor(Color.parseColor("#1E88E5")) // azul inicial
        }

        calculoTotal!!.setText("Precio = " + total + " €")
    }

    private fun calcularCategoria(grupo: RadioGroup?, personasInput: EditText?): Int {
        if (grupo == null || personasInput == null) return 0

        var personas: Int
        try {
            personas = personasInput.getText().toString().toInt()
        } catch (e: NumberFormatException) {
            personas = 0
        }

        val checkedId = grupo.getCheckedRadioButtonId()
        if (checkedId == -1) return 0

        val rb = findViewById<RadioButton?>(checkedId)
        val texto = rb.getText().toString()

        var precio = 0
        if (texto.contains("-")) {
            val partes: Array<String?> =
                texto.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            try {
                precio = partes[1]!!.trim { it <= ' ' }.toInt()
            } catch (ignored: NumberFormatException) {
            }
        }

        return precio * personas
    }
}