package com.juliandev.grifo.activity.apiSunat.sunatModel

import com.google.gson.annotations.SerializedName

data class DniSunatModel (
    @SerializedName("success") val success:Boolean,
    @SerializedName("dni")  val dni: String,
    @SerializedName("nombre") val nombre: String
)