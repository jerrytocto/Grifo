package com.juliandev.grifo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.juliandev.grifo.activity.InicioActivity
import com.juliandev.grifo.activity.elegirTipoUsuarioActivity
import com.juliandev.grifo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gmail: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

    }

    private fun initListener() {
        recibirDatosIniciarSesion()
        binding.txtNuevoUsuario.setOnClickListener { registrarUsuario() }
    }

    private fun registrarUsuario() {
        val intent = Intent(this,elegirTipoUsuarioActivity::class.java)
        startActivity(intent)
    }

    private fun recibirDatosIniciarSesion() {
        binding.btnIniciarSesion.setOnClickListener {
            password = binding.edtPassword.text.toString()
            gmail = binding.edtEmail.text.toString()
            if (password.isNullOrEmpty() || gmail.isNullOrEmpty()) {
                Toast.makeText(this, "Todos los  campos son requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            iniciarSesion()
        }
    }

    private fun iniciarSesion() {
        //Aquí se trabaja la configuración para consumir la API de inicio de sesión
        val intent = Intent(this, InicioActivity::class.java)
        intent.putExtra("gmail", gmail)
        startActivity(intent)

        binding.edtPassword.text = null
        binding.edtEmail.text = null
    }





}