package com.juliandev.grifo.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.juliandev.grifo.R
import com.juliandev.grifo.databinding.FragmentVincularUserBinding
import com.juliandev.grifo.ui.viewModel.VincularUserViewModel
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class VincularUserFagment : Fragment() {
    private val vincularUserViewModel: VincularUserViewModel by viewModels()
    private var _binding: FragmentVincularUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var opcionesABuscar: List<String>
    private var opcionSeleccionada: String? = null
    private var numDocumento:String ?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVincularUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        vincularUserViewModel.cargarOpcionesABuscar()
        mostrarOpcionesParaBuscar()
        recibirOpcionParaFiltrar()

        lavedataUserDNI()
        lavedataUserRUC()

        recepcionarDatosParaFiltrar()
        mostrarPersonaPorDNI()


        return root
    }


    private fun mostrarOpcionesParaBuscar() {
        vincularUserViewModel.cargarOpciones.observe(viewLifecycleOwner, Observer { opciones->
            opcionesABuscar= opciones
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, opciones)
            binding.searchBy.setAdapter(adapter)
        })
    }
    private fun recibirOpcionParaFiltrar() {
        binding.searchBy.setOnItemClickListener { parent, view, position, id ->
            opcionSeleccionada = opcionesABuscar[position]
        }
    }

    private fun recepcionarDatosParaFiltrar(){
        binding.btnEnviar.setOnClickListener {
            numDocumento = binding.numDocument.text.toString()
            vincularUserViewModel.recepcionarDatosParaBuscar(numDocumento.orEmpty(),opcionSeleccionada.orEmpty())

        }
    }

    private fun mostrarPersonaPorDNI() {
        vincularUserViewModel.usuarioPorDni.observe(viewLifecycleOwner, Observer { usuarioModel->
            binding.txtNomRazonSocial.text=usuarioModel.nombre
            binding.txtDni.text=usuarioModel.dni
        })
    }
    private fun lavedataUserDNI(){
        vincularUserViewModel.camposVacios.observe(viewLifecycleOwner, Observer { mensaje->
            Toast.makeText(requireContext(),mensaje, Toast.LENGTH_SHORT).show()
        })

        vincularUserViewModel.dniInvalido.observe(viewLifecycleOwner, Observer { mensaje->
            Toast.makeText(requireContext(),mensaje, Toast.LENGTH_SHORT).show()
        })
        vincularUserViewModel.usuarioNoEncontrado.observe(viewLifecycleOwner, Observer { mensaje->
            Toast.makeText(requireContext(),mensaje, Toast.LENGTH_SHORT).show()
        })
        vincularUserViewModel.viewUserFoundDNI.observe(viewLifecycleOwner, Observer { status->
            binding.llyDatosPer.isVisible=status
        })
        vincularUserViewModel.viewBtnVincular.observe(viewLifecycleOwner, Observer { status->
            binding.btnVincular.isVisible=status
        })
    }


    private fun lavedataUserRUC(){
        vincularUserViewModel.RUCInvalido.observe(viewLifecycleOwner, Observer { mensaje->
            Toast.makeText(requireContext(),mensaje, Toast.LENGTH_SHORT).show()
        })
        vincularUserViewModel.usuarioPorRuc.observe(viewLifecycleOwner, Observer { user->
            binding.edtDatosPer.text=user.nombreORazonSocial
            binding.txtNumRUC.text=user.ruc
            binding.tvEstadoRuc.text=user.estadoDelContribuyente
        })
        vincularUserViewModel.viewUserFoundRUC.observe(viewLifecycleOwner, Observer { estado->
            binding.cvDatosPerRUC.isVisible=estado
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}