package com.juliandev.grifo.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.juliandev.grifo.databinding.ActivityMainBinding
import com.juliandev.grifo.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()

    private lateinit var gmail: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        irPantallaPrincipal()
        errorInicioSesion()
        registrarUsuario()

    }


    private fun initListener() {
        recibirDatosIniciarSesion()
        binding.txtNuevoUsuario.setOnClickListener { mainViewModel.registrarUsuario() }
    }

    private fun registrarUsuario() {
        mainViewModel.irVentanaRegistroUser.observe(this, Observer {
            if(it){
                val intent = Intent(this,RegistrarUsuarioActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun recibirDatosIniciarSesion() {
        binding.btnIniciarSesion.setOnClickListener {
            password = binding.edtPassword.text.toString()
            gmail = binding.edtEmail.text.toString()
            mainViewModel.verificarCamposIniciarSesion(gmail,password)
        }
    }
    private fun irPantallaPrincipal() {
        mainViewModel.irVentanaInicio.observe(this, Observer { irPantalla->
            if(irPantalla){
                val intent = Intent(this,InicioActivity::class.java)
                startActivity(intent)
                binding.edtPassword.text = null
                binding.edtEmail.text = null
            }
        })
    }
    private fun errorInicioSesion() {
        mainViewModel.mensajeErrorInicioSesion.observe(this, Observer { mensajeError->
            if(!mensajeError.isNullOrEmpty()){
                Toast.makeText(this,mensajeError, Toast.LENGTH_SHORT).show()
            }
        })
    }

}