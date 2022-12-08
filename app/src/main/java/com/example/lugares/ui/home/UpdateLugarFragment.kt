package com.example.lugares.ui.home

import android.app.AlertDialog
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
import com.example.lugares.UpdateLugarFragmentArgs
import com.example.lugares.databinding.FragmentAddLugar2Binding
import com.example.lugares.databinding.FragmentUpdateLugarBinding
import com.example.lugares.model.Lugar
import com.example.lugares.viewmodel.HomeViewModel

class UpdateLugarFragment : Fragment() {
    //Recuperar los elementos enviados
    private val args by navArgs<UpdateLugarFragmentArgs>()

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateLugarBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.etNombre3.setText(args.lugarArg.nombre)
        binding.etCorreo3.setText(args.lugarArg.correo)
        binding.etTelefono3.setText(args.lugarArg.telefono)
        binding.etWeb3.setText(args.lugarArg.web)

        binding.btActualizar.setOnClickListener{updateLugar()}
        binding.btDelete.setOnClickListener{deleteLugar()}


        return binding.root
    }

    private fun updateLugar(){
        val nombre=binding.etNombre3.text.toString()
        val correo=binding.etCorreo3.text.toString()
        val telefono=binding.etTelefono3.text.toString()
        val web=binding.etWeb3.text.toString()
        if(nombre.isNotEmpty()){
            val lugar = Lugar(args.lugarArg.id,nombre,correo,telefono, web,args.lugarArg.rutaAudio, args.lugarArg.rutaImagen)
            homeViewModel.addLugar(lugar)
            Toast.makeText(requireContext(),getString((R.string.ms_UpdateLugar)), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_homeFragment)
        }else{
            Toast.makeText(requireContext(),getString(R.string.ms_faltaValores), Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteLugar(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.si)){_,_ ->
            homeViewModel.deleteLugar(args.lugarArg)
            Toast.makeText(requireContext(),
            getString(R.string.deleted) + "${args.lugarArg.nombre}!",
                Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateLugarFragment_to_homeFragment)
        }
        builder.setNegativeButton(getString(R.string.no)){_,_->}
        builder.setTitle(R.string.deleted)
        builder.setMessage(getString(R.string.seguroDeBorrar) + "${args.lugarArg.nombre}?")
        builder.create().show()
    }
}