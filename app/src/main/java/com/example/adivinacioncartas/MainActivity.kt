package com.example.adivinacioncartas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adivinacioncartas.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (binding.txtNombre.text.toString().isNotEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Perfecto ${binding.txtNombre.text}, quieres empezar",
                    Snackbar.LENGTH_LONG
                ).setAction("Ok") {
                    val intent = Intent(applicationContext, GameActivity::class.java)
                    val bundle= Bundle()
                    bundle.putString("nombre", binding.txtNombre.text.toString())
                    intent.putExtra( "datos", bundle)
                    startActivity(intent)
                }.show()

            } else {
                Snackbar.make(binding.root, "Por favor introduce nombre", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}