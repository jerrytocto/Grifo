package com.juliandev.grifo.activity.apiSunat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.juliandev.grifo.R
import com.juliandev.grifo.activity.apiSunat.service.SunatService
import com.juliandev.grifo.activity.apiSunat.sunatModel.DniSunatModel
import com.juliandev.grifo.activity.apiSunat.sunatModel.SunatModel
import com.juliandev.grifo.activity.registrarUsuarioActivity
import com.juliandev.grifo.databinding.ActivityApiSunatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiSunatActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var binding: ActivityApiSunatBinding
    private lateinit var opcionesABuscar: List<String>
    private var opcionSeleccionada: String? = null
    private var numDocumento: String? = ""
    val token = "UHExV1MsA1Egr6nP1chINIkFbS7IcrSTUI8UaTsWmJ4JIBC7emXYzQWAHNyJ"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApiSunatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = getRetrofit()

        cargarOpcionesABuscar()
        recibirOpcionParaFiltrar()
        recepcionarTodosDatosPeticion()
        initOnklick()
    }

    private fun initOnklick() {
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this,registrarUsuarioActivity::class.java )
            startActivity(intent)
        }
        binding.btnVincular.setOnClickListener { vincularPersonaUsuario() }
    }

    private fun vincularPersonaUsuario() {
        when(opcionSeleccionada){
            "DNI"-> recibirDatosVincularUsuarioConDni()
            "RUC"-> recibirDatosVincularUsuarioConRUC()
            else -> toast(this,"Elija una opción")
        }
    }

    private fun recibirDatosVincularUsuarioConRUC() {
        val editNombresPer = binding.edtDatosPer.text.toString()
        val txtNumRUC = binding.txtNumRUC.text.toString()
        val editNumTelefono = binding.editNumTelefono.text.toString().orEmpty()
        val nuevaDireccion = binding.editDireccionActual.text.toString().orEmpty()

        val intent= Intent(this,registrarUsuarioActivity::class.java).apply {
            putExtra("editNombresPer",editNombresPer)
            putExtra("txtNumRUC",txtNumRUC)
            putExtra("editNumTelefono",editNumTelefono)
            putExtra("nuevaDireccion",nuevaDireccion)
        }
        startActivity(intent)
    }

    private fun recibirDatosVincularUsuarioConDni() {
        val numDni = binding.txtDni.text.toString()
        val namePersona = binding.txtNomRazonSocial.text.toString()
        val editNumTelefono = binding.editNumTelefono.text.toString().orEmpty()
        val nuevaDireccion = binding.editDireccionActual.text.toString().orEmpty()

        val intent= Intent(this,registrarUsuarioActivity::class.java).apply {
            putExtra("numDni",numDni)
            putExtra("namePersona",namePersona)
            putExtra("editNumTelefono",editNumTelefono)
            putExtra("nuevaDireccion",nuevaDireccion)
        }
        startActivity(intent)
        //Log.i("julianDev","Nombre: ${namePersona}")
    }

    //Configuración de retrofit
    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.migo.pe/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun cargarOpcionesABuscar() {
        opcionesABuscar = listOf<String>("RUC", "DNI")
        val adapter = ArrayAdapter(this, R.layout.list_item, opcionesABuscar)
        binding.searchBy.setAdapter(adapter)
    }

    private fun recibirOpcionParaFiltrar() {
        binding.searchBy.setOnItemClickListener { parent, view, position, id ->
            opcionSeleccionada = opcionesABuscar[position]
        }
    }

    private fun recepcionarTodosDatosPeticion() {
        binding.btnEnviar.setOnClickListener {
            numDocumento = binding.numDocument.text.toString()

            if (numDocumento.isNullOrEmpty() || opcionSeleccionada.isNullOrEmpty()) {
                toast(this, "Todos los campos son requeridos")
            } else {
                when (opcionSeleccionada) {
                    "DNI" -> buscarPersonaDNI(numDocumento.orEmpty())
                    "RUC" -> buscarPersonaRUC(numDocumento.orEmpty())
                }
            }
        }
    }

    private fun buscarPersonaDNI(numberDocument: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (numberDocument.length == 8) {
                val dniRequest = SunatService.DNIRequest(token, numberDocument)
                val myResponse = retrofit.create(SunatService::class.java)
                    .searchRegisterByDNI(dniRequest)
                mostrarPersonaDNI(myResponse)
            } else {
                withContext(Dispatchers.Main) {
                    toast(this@ApiSunatActivity, "EL campo DNI debe tener 8 dígitos")
                }
            }
        }
    }

    private suspend fun mostrarPersonaDNI(dniSunatModel: Response<DniSunatModel>) {
        withContext(Dispatchers.Main) {
            if (dniSunatModel.isSuccessful && dniSunatModel.body() != null) {
                val personaSunat = dniSunatModel.body()
                Log.i("jerryDev", "${personaSunat}")
                if (personaSunat != null) {
                    binding.txtNomRazonSocial.text = personaSunat.nombre
                    binding.txtDni.text = personaSunat.dni
                    binding.llyDatosPer.visibility = View.VISIBLE
                    binding.cvDatosPerRUC.visibility = View.GONE
                    binding.btnVincular.visibility= View.VISIBLE
                }
            } else {
                toast(this@ApiSunatActivity, "Usuario no encontrado")
                binding.edtDatosPer.text = null
                binding.txtDni.text = null
                binding.llyDatosPer.visibility = View.GONE
                binding.btnVincular.visibility= View.GONE
                binding.cvDatosPerRUC.visibility = View.GONE
                binding.btnVincular.visibility= View.GONE
            }
        }
    }

    private fun buscarPersonaRUC(numberDocument: String) {
        CoroutineScope(Dispatchers.IO).launch {

            if (numberDocument.length == 11) {
                val dniRequest = SunatService.DNIRequest(token, numberDocument)
                val myResponse = retrofit.create(SunatService::class.java)
                    .searchRegisterByRUC(numDocumento.toString(), token)
                mostrarPersonaRUC(myResponse)

            } else {
                withContext(Dispatchers.Main) {
                    toast(this@ApiSunatActivity, "EL campo RUC debe tener 11 dígitos")
                }
            }
        }
    }
    private suspend fun mostrarPersonaRUC(sunatModel: Response<SunatModel>) {
        withContext(Dispatchers.Main) {
            if (sunatModel.isSuccessful && sunatModel.body() != null) {
                val personaSunatRuc = sunatModel.body()
                //Log.i("jerryDev", " ${personaSunatRuc}")
                if (personaSunatRuc != null) {
                    //Mostrar Los datos en la vista
                    binding.edtDatosPer.text = personaSunatRuc.nombreORazonSocial
                    binding.txtNumRUC.text = personaSunatRuc.ruc
                    binding.txtEstado.text = personaSunatRuc.estadoDelContribuyente
                    binding.cvDatosPerRUC.visibility = View.VISIBLE
                    binding.btnVincular.visibility= View.VISIBLE
                    binding.llyDatosPer.visibility = View.GONE

                }
            } else {
                toast(this@ApiSunatActivity, "El RUC ingresado no se encuentra registrado")
                binding.txtNomRazonSocial.text = null
                binding.txtNumRUC.text = null
                binding.txtEstado.text = null
                binding.cvDatosPerRUC.visibility = View.GONE
                binding.btnVincular.visibility= View.GONE
                binding.llyDatosPer.visibility = View.GONE
                binding.btnVincular.visibility= View.GONE

            }
        }
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}