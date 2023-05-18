package com.juliandev.grifo.ui.view.activity


import android.content.Intent

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.juliandev.grifo.databinding.ActivityRegistrarUsuarioBinding
import com.juliandev.grifo.ui.viewModel.RegistrarUsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistrarUsuarioBinding
    private val registrarUsuarioViewModel:RegistrarUsuarioViewModel by viewModels()

    private var imagenSeleccionada: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrarUser.setOnClickListener {
            val apePater = binding.txtApePaterno.text.toString()
            val apeMater = binding.txtapeMaterno.text.toString()
            val nombre = binding.txtUserNombre.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()

            registrarUsuarioViewModel.recibirDatosRegistrar(apePater, apeMater,nombre,email,password)
        }
        registrarUsuarioViewModel.verificarCampos.observe(this, Observer {mensaje->
            Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        })
        registrarUsuarioViewModel.verificarCorreo.observe(this, Observer { mensaje->
            Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initListener() {
//        binding.txtAfiliarPersona.setOnClickListener { afiliarUsuario() }
//        binding.tvAfiliarNum.setOnClickListener { afiliarNumeroTelefono() }

    }

//    private fun afiliarNumeroTelefono() {
//        val intem = Intent(this,ApiWhatsAppActivity::class.java)
//        startActivity(intem)
//    }

//    private fun afiliarUsuario() {
//        val intent=Intent(this, ApiSunatActivity::class.java)
//        startActivity(intent)
////        binding.linLayRegistrarUsuario.visibility = View.GONE
////        binding.lyAfiliarPersona.visibility = View.VISIBLE
//    }

    // Función para abrir la galería y seleccionar una imagen
    private val seleccionarFoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imagenSeleccionada = uri
            binding.imageView.setImageURI(imagenSeleccionada)

            CoroutineScope(Dispatchers.IO).launch {
                val inputStream = contentResolver.openInputStream(imagenSeleccionada!!)
                val bytes = inputStream!!.readBytes()
                Log.d("API_SUNAT",bytes.contentToString())
            }
        }
    }

    // Función para manejar el evento onClick del TextView y del ImageView
    fun abrirGaleria(view: View) {
        seleccionarFoto.launch("image/*")
    }

    fun recibirDatosRegistrarUsuario(){
        val inputStream = contentResolver.openInputStream(imagenSeleccionada!!)
        val bytes = inputStream!!.readBytes()
    }

    fun iniciarSesion(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}