package com.example.buscaminakotlin

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    var etNumero: EditText? = null
    var btnCrear: Button? = null
    var btnReiniciar: Button? = null
    var gridLayout: GridLayout? = null
    var tvInfo: TextView? = null
    var totalBotones: Int = 0
    var minas: Int = 0
    var fallos: Int = 0
    var maxFallos: Int = 0
    var minasEncontradas: Int = 0
    var listaMinas: ArrayList<Int?> = ArrayList<Int?>()
    var juegoTerminado: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNumero = findViewById<EditText?>(R.id.etNumero)
        btnCrear = findViewById<Button?>(R.id.btnCrear)
        gridLayout = findViewById<GridLayout?>(R.id.gridLayout)
        tvInfo = findViewById<TextView?>(R.id.tvInfo)
        btnReiniciar = findViewById<Button?>(R.id.btnReiniciar)

        btnCrear!!.setOnClickListener(View.OnClickListener { v: View? -> crearJuego() })
        btnReiniciar!!.setOnClickListener(View.OnClickListener { v: View? -> reiniciar() })
    }

    private fun crearJuego() {
        val texto = etNumero!!.getText().toString().trim { it <= ' ' }
        if (texto.isEmpty()) return

        totalBotones = texto.toInt()
        if (totalBotones < 4 || totalBotones > 30) {
            Toast.makeText(this, "Minimo 4 y máximo 30 botones", Toast.LENGTH_SHORT).show()
            return
        }

        gridLayout!!.removeAllViews()
        listaMinas.clear()
        fallos = 0
        minasEncontradas = 0
        juegoTerminado = false

        minas = totalBotones / 4
        maxFallos = totalBotones / 2 // 50% de los botones

        generarMinas()

        gridLayout!!.post(object : Runnable {
            override fun run() {
                configurarGrid()
            }
        })
    }

    private fun generarMinas() {
        val random = Random()
        while (listaMinas.size < minas) {
            val n = random.nextInt(totalBotones) + 1
            if (!listaMinas.contains(n)) listaMinas.add(n)
        }
    }

    private fun configurarGrid() {
        val columnas = ceil(sqrt(totalBotones.toDouble())).toInt()
        val filas = ceil(totalBotones.toDouble() / columnas).toInt()
        gridLayout!!.setColumnCount(columnas)
        gridLayout!!.setRowCount(filas)

        val anchoGrid = gridLayout!!.getWidth()
        val altoGrid = gridLayout!!.getHeight()

        val espacioPorBoton = anchoGrid / columnas - 12
        val altoPorBoton = altoGrid / filas - 12

        val buttonSize = min(espacioPorBoton, altoPorBoton)

        for (i in 1..totalBotones) {
            val b = Button(this)
            b.setText(i.toString())
            b.setBackgroundColor(Color.LTGRAY)

            val params = GridLayout.LayoutParams()
            params.width = buttonSize
            params.height = buttonSize
            params.setMargins(6, 6, 6, 6)
            b.setLayoutParams(params)

            val finalI = i
            b.setOnClickListener(View.OnClickListener { v: View? -> verificar(b, finalI) })
            gridLayout!!.addView(b)
        }

        actualizarTexto()
    }

    private fun verificar(b: Button, num: Int) {
        if (juegoTerminado) return

        if (listaMinas.contains(num)) {
            b.setBackgroundColor(Color.RED)
            b.setText("✔")
            minasEncontradas++
            Toast.makeText(this, "¡Has encontrado una mina!", Toast.LENGTH_SHORT).show()
        } else {
            b.setBackgroundColor(Color.WHITE)
            b.setEnabled(false)
            fallos++
        }

        // Revisar fin del juego: pérdida
        if (fallos >= maxFallos) {
            Toast.makeText(this, "¡Has perdido!", Toast.LENGTH_SHORT).show()
            juegoTerminado = true
            bloquearTodosLosBotones()
        }

        // Revisar fin del juego: victoria
        if (minasEncontradas == minas) {
            Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_SHORT).show()
            juegoTerminado = true
            bloquearTodosLosBotones()
        }

        actualizarTexto()
    }

    private fun bloquearTodosLosBotones() {
        for (i in 0..<gridLayout!!.getChildCount()) {
            val v = gridLayout!!.getChildAt(i)
            v.setEnabled(false)
        }
    }

    private fun actualizarTexto() {
        tvInfo!!.setText(
            "Fallos: " + fallos +
                    " | Fallos posibles: " + maxFallos +
                    " | Minas: " + minas +
                    " | Encontradas: " + minasEncontradas
        )
    }

    private fun reiniciar() {
        gridLayout!!.removeAllViews()
        etNumero!!.setText("")
        tvInfo!!.setText("Fallos: 0 | Fallos posibles: 0 | Minas: 0 |Encontradas: 0")
        listaMinas.clear()
        juegoTerminado = false
        minasEncontradas = 0
        fallos = 0
    }
}