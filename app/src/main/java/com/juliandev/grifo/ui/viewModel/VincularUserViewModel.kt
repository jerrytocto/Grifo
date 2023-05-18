package com.juliandev.grifo.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliandev.grifo.data.modelo.DniSunatModel
import com.juliandev.grifo.data.modelo.SunatModel
import com.juliandev.grifo.domain.apisunatCU.ConsultarPorDNICU
import com.juliandev.grifo.domain.apisunatCU.ConsultarPorRUCU
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class VincularUserViewModel : ViewModel() {

    var getConsultarPorDNICU=ConsultarPorDNICU()
    var getConsultarPorRUCU=ConsultarPorRUCU()

    val cargarOpciones = MutableLiveData<List<String>>()
    val camposVacios = MutableLiveData<String>()
    val dniInvalido = MutableLiveData<String>()
    val usuarioNoEncontrado= MutableLiveData<String>()
    val usuarioPorDni = MutableLiveData<DniSunatModel>()
    val viewUserFoundDNI= MutableLiveData<Boolean>()
    val viewBtnVincular = MutableLiveData<Boolean>()
    val RUCInvalido = MutableLiveData<String>()

    val usuarioPorRuc = MutableLiveData<SunatModel>()
    val viewUserFoundRUC = MutableLiveData<Boolean>()

    companion object {
        var TOKEN: String = "hV9JtWjUezWJWyCygzBvmW4iKi8gkuqOW9CInSWmSpxVeHFY7PW8y5Le6XdP"
    }

    fun cargarOpcionesABuscar() {
        cargarOpciones.postValue(listOf("DNI", "RUC", "CEX"))
    }

    fun recepcionarDatosParaBuscar(numDocumento: String, opcionSeleccionada: String) {
        if (numDocumento.isNullOrEmpty() || opcionSeleccionada.isNullOrEmpty()) {
            camposVacios.postValue("Todos los campos son requeridos")
            viewUserFoundDNI.postValue(false)
            viewUserFoundRUC.postValue(false)
            viewBtnVincular.postValue(false)

        } else {
            when (opcionSeleccionada) {
                "DNI" -> buscarPersonaDNI(numDocumento.orEmpty())
                "RUC" -> buscarPersonaRUC(numDocumento.orEmpty())
            }
        }
    }

    private fun buscarPersonaDNI(numDocumento: String) {
        if(numDocumento.length==8){
            viewModelScope.launch {
                val resultUser = getConsultarPorDNICU.invoke(TOKEN,numDocumento)
                if (resultUser!=null){
                    usuarioPorDni.postValue(resultUser!!)
                    viewUserFoundDNI.postValue(true)
                    viewBtnVincular.postValue(true)
                    //Log.d("personaDNI","$resultUser")
                }else{
                    //Log.d("personaDNI","Usuario no encontrado")
                    viewUserFoundDNI.postValue(false)
                    viewBtnVincular.postValue(false)
                    usuarioNoEncontrado.postValue("Usuario no encontrado")
                }
            }
        }else{
            dniInvalido.postValue("El DNI debe tener 8 dígitos")
        }
    }

    private fun buscarPersonaRUC(numDocumento: String) {
        if(numDocumento.length==11){
            viewModelScope.launch {
                val resultUser =getConsultarPorRUCU.invoke(TOKEN,numDocumento)
                if (resultUser!=null){
                    usuarioPorRuc.postValue(resultUser!!)
                    viewUserFoundDNI.postValue(false)
                    viewUserFoundRUC.postValue(true)
                    viewBtnVincular.postValue(true)
                    //Log.d("personaRUC","${resultUser}")

                }else{
                    usuarioNoEncontrado.postValue("Usuario no encontrado")
                    //Log.d("personaRUC","El usuario no ha sido registrado")
                    viewUserFoundDNI.postValue(false)
                    viewUserFoundRUC.postValue(false)
                    viewBtnVincular.postValue(false)
                }
            }
        }else{
            RUCInvalido.postValue("El RUC debe tener 11 dígitos")
        }
    }

}