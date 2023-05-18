package com.juliandev.grifo.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juliandev.grifo.databinding.ActivityElegirTipoUsuarioBinding

class elegirTipoUsuarioActivity : AppCompatActivity() {

    private lateinit var binding:ActivityElegirTipoUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElegirTipoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.btnRegistrarPersona.setOnClickListener { showViewRegisterPerson() }
       // binding.btnRegistrarEmpresa.setOnClickListener { showViewRegisterCompany() }
    }

    private fun showViewRegisterCompany() {

    }

    private fun showViewRegisterPerson() {
        val intent = Intent(this, RegistrarUsuarioActivity::class.java)
        startActivity(intent)
    }
}