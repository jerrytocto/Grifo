package com.juliandev.grifo.data.network

import androidx.lifecycle.viewmodel.CreationExtras
import com.juliandev.grifo.core.RetrofitHelper
import com.juliandev.grifo.data.modelo.DniSunatModel
import com.juliandev.grifo.data.modelo.SunatModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SunatService {

    private val retrofit= RetrofitHelper.getRetrofit()

    suspend fun buscarUsuarioPorDNI(token:String,numDNI:String):DniSunatModel?{
        return withContext(Dispatchers.IO){
            val dniRequest = SunatIntefaz.DNIRequest(token, numDNI)
            val response = retrofit.create(SunatIntefaz::class.java).searchRegisterByDNI(dniRequest)
            response.body()
        }
    }
    suspend fun buscarUsuarioPorRUC(token:String,numRUC:String):SunatModel?{
        return withContext(Dispatchers.IO){
            //val rucRequest  = SunatIntefaz.DNIRequest(token,numRUC)
            val response = retrofit.create(SunatIntefaz::class.java).searchRegisterByRUC(numRUC,token)
            response.body()
        }
    }
}