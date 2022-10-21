package com.example.lugares.ui.lugar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lugares.R
import com.example.lugares.databinding.FragmentAddLugar2Binding
import com.example.lugares.databinding.FragmentLugarBinding
import com.example.lugares.model.Lugar
import com.example.lugares.viewmodel.LugarViewModel


class AddLugarFragment : Fragment() {

    private var _binding: FragmentAddLugar2Binding? = null
    private val binding get() = _binding!!

    private lateinit var lugarViewModel: LugarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLugar2Binding.inflate(inflater, container, false)

        lugarViewModel = ViewModelProvider( this).get(LugarViewModel::class.java)

        binding.btAddLugar.setOnClickListener { insertaLugar()}

        return binding.root
    }


    private fun insertaLugar(){
        val nombre = binding.etNombre.text.toString()
        val correo = binding.etCorreo.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web = binding.etWeb.text.toString()
        if(validos(nombre,correo,telefono,web)){
            val lugar = Lugar(0, nombre, correo, telefono, web,0.0 ,0.0, 0, "", "")
            lugarViewModel.addLugar(lugar)
            Toast.makeText(requireContext(),getString(R.string.msgLugarAgregado), Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(),getString(R.string.msgFaltanDatos), Toast.LENGTH_LONG).show()
        }
    }

    private fun validos(nombre: String, correo:String, telefono: String, web: String): Boolean{
        return !(nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || web.isEmpty())
    }


}