package com.juliandev.grifo.ui.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juliandev.grifo.domain.iniciosesionCU.ValidarCamposInicioSesionCU


class MainViewModel : ViewModel() {

    val irVentanaInicio = MutableLiveData<Boolean>()
    val irVentanaRegistroUser = MutableLiveData<Boolean>()
    val mensajeErrorInicioSesion = MutableLiveData<String>()

    fun verificarCamposIniciarSesion(correo: String, password: String){
        if (correo.isEmpty() || password.isEmpty()){
            mensajeErrorInicioSesion.value ="Todos los campos son obligatorios"
        }else{
            irVentanaInicio.value=true
        }
    }

    fun registrarUsuario(){
        irVentanaRegistroUser.value=true
    }
}