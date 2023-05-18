package com.juliandev.grifo.ui.viewModel

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrarUsuarioViewModel: ViewModel() {
    val cargarOpciones = MutableLiveData<List<String>>()

    val verificarCampos = MutableLiveData<String>()
    val verificarCorreo = MutableLiveData<String>()

    fun recibirDatosRegistrar(nombre:String, apePater:String, apeMater:String,gmail:String,
                              password:String){

        if(apePater.isNullOrEmpty() || apeMater.isNullOrEmpty() ||nombre.isNullOrEmpty() || gmail.isNullOrEmpty() || password.isNullOrEmpty()){
            verificarCampos.postValue("Por favor, completar campos obligatorios")
        }else{
             if(gmail.isCorreoElectronico()){
                 //Verificar correo a la api
             }else{
                 //La variable correo no tiene un formato de correo electrónico
                 verificarCorreo.postValue("El correo electrónico no es válido")
             }
        }
    }

    //verificar el formato del correo electrónico
    fun String.isCorreoElectronico():Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(this).matches()
    }
}