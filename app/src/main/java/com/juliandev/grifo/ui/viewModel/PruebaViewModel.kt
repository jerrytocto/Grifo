package com.juliandev.grifo.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PruebaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    val text: LiveData<String>
        get() = _text

    init {
        _text.value = "ESTA ES LA PARTE DE PRUEBA"
    }
}