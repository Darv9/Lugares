package com.example.lugares.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lugares.R
import com.example.lugares.databinding.FragmentAddLugar2Binding
import com.example.lugares.model.Lugar
import com.example.lugares.viewmodel.HomeViewModel

class UpdateLugarFragment : Fragment() {
    //Recuperar los elementos enviados
    private val args by navArgs<UpdateLugarFramgentArgs>()

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLugar2Binding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.etNombre.setText(args.lugarArg.nombre)
        binding.etCorreoLugar.setText(args.lugarArg.correo)
        binding.etTelefono.setText(args.lugarArg.telefono)
        binding.etWeb.setText(args.lugarArg.web)

        binding.btUpdateLugar.setOnClickListener{updateLugar()}
        //binding.btDeleteLugar.setOnClickListener{deleteLugar()}


        return binding.root
    }

    private fun updateLugar(){
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(args.LugarArg.id,nombre,telefono,web)
            homeViewModel.addLugar(lugar)
            Toast.makeText(requireContext(),getString((R.string.ms_UpdateLugar)), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_UpdateLugarFragment_to_nav_home)
        }else{
            Toast.makeText(requireContext(),getString(R.string.ms_faltaValores), Toast.LENGTH_LONG).show()
        }
    }
}