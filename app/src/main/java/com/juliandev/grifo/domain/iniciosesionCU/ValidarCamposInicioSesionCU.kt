package com.juliandev.grifo.domain.iniciosesionCU

class ValidarCamposInicioSesionCU(correo:String, password:String) {
    val correo = correo
    val password = password

    operator fun invoke():Boolean{
        if(!correo.isNullOrEmpty() || !password.isNullOrEmpty()){
            return true
        }
        return false
    }
}