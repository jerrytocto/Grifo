package com.juliandev.grifo.domain.apisunatCU

import com.juliandev.grifo.data.ApiSunatRepository
import com.juliandev.grifo.data.modelo.DniSunatModel
import com.juliandev.grifo.data.modelo.SunatModel

class ConsultarPorRUCU {
    private val repository = ApiSunatRepository()

    suspend operator fun invoke(token:String,numDocumento:String): SunatModel?{
        return repository.obtenerUsuarioPorRUC(token,numDocumento)
    }
}