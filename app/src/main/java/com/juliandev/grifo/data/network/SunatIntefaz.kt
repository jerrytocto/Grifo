package com.juliandev.grifo.data.network

import com.juliandev.grifo.data.modelo.DniSunatModel
import com.juliandev.grifo.data.modelo.SunatModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SunatIntefaz {

    @GET("sunat/ruc/{ruc}")
    suspend fun searchRegisterByRUC(
        @Path("ruc") ruc: String,
        @Query("token") token: String
    ): Response<SunatModel>

    @POST("dni")
    suspend fun searchRegisterByDNI(
        @Body dniRequest: DNIRequest
    ): Response<DniSunatModel>

    data class DNIRequest(
        val token: String,
        val dni: String
    )
}