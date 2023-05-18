package com.juliandev.grifo.domain.apisunatCU

import com.juliandev.grifo.data.ApiSunatRepository
import com.juliandev.grifo.data.modelo.DniSunatModel

class ConsultarPorDNICU {

    private val repository = ApiSunatRepository()

    suspend operator fun invoke(token:String,numDocumento:String):DniSunatModel?{
        return repository.obtenerUsuarioPorDNI(token,numDocumento)
    }
}