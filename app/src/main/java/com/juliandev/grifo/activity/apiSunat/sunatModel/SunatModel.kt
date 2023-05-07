package com.juliandev.grifo.activity.apiSunat.sunatModel

import com.google.gson.annotations.SerializedName

data class SunatModel (
    @SerializedName("ruc") val ruc: String,
    @SerializedName("nombre_o_razon_social") val nombreORazonSocial:String,
    @SerializedName("estado_del_contribuyente") val estadoDelContribuyente: String
)