package com.juliandev.grifo.data

import com.juliandev.grifo.data.modelo.DniSunatModel
import com.juliandev.grifo.data.modelo.SunatModel
import com.juliandev.grifo.data.network.SunatService

class ApiSunatRepository {

    private val api = SunatService()

    suspend fun obtenerUsuarioPorDNI(token:String,numDocumento:String): DniSunatModel?{
        val response = api.buscarUsuarioPorDNI(token,numDocumento)
        return response
    }
    suspend fun obtenerUsuarioPorRUC(token:String, numDocumento: String):SunatModel?{
        val response = api.buscarUsuarioPorRUC(token, numDocumento)
        return response
    }
}