package com.juliandev.grifo.activity


import android.content.Intent

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.juliandev.grifo.databinding.ActivityRegistrarUsuarioBinding
import com.juliandev.grifo.MainActivity
import com.juliandev.grifo.activity.apiSunat.ApiSunatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class registrarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistrarUsuarioBinding
    private var imagenSeleccionada: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.txtAfiliarPersona.setOnClickListener { afiliarUsuario() }
    }

    private fun afiliarUsuario() {
        val intent=Intent(this,ApiSunatActivity::class.java)
        startActivity(intent)
//        binding.linLayRegistrarUsuario.visibility = View.GONE
//        binding.lyAfiliarPersona.visibility = View.VISIBLE
    }

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
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    fun afiliarUsuario(view:View){

    }

}