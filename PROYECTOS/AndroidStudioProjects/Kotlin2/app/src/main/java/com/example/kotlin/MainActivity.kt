package com.example.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       binding.button.setOnClickListener {
           val nro1 = binding.et1.text.toString().toIntOrNull() ?: 0
           val nro2 = binding.et2.text.toString().toIntOrNull() ?: 0
           val suma = nro1 + nro2
           binding.tv3.text = "Resultado:~$suma"
       }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Solo si tienes res/menu/activity_main.xml
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    @SuppressLint("SetTextI18n")
    fun sumar(view: View?) {
        val valor1 = binding.et1.text.toString().trim()
        val valor2 = binding.et2.text.toString().trim()

        if (valor1.isNotEmpty() && valor2.isNotEmpty()) {
            val suma = valor1.toInt() + valor2.toInt()
            binding.tv3.text = "Resultado: $suma"

        } else {
            binding.tv3.text = "Por favor ingrese ambos valores"

        }
    }
}
