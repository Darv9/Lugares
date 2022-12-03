package com.example.lugares.ui.home

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lugares.R
import com.example.lugares.databinding.FragmentAddLugar2Binding
import com.example.lugares.model.Lugar
import com.example.lugares.utilidades.AudioUtiles
import com.example.lugares.utilidades.ImagenUtiles
import com.example.lugares.viewmodel.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class AddLugarFragment : Fragment() {

    private var _binding: FragmentAddLugar2Binding? = null
    private val binding get() = _binding!!

    private lateinit var lugarViewModel: HomeViewModel
    private lateinit var audioUtiles: AudioUtiles
    private lateinit var imagenUtiles: ImagenUtiles
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLugar2Binding.inflate(inflater, container, false)

        lugarViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.btAgregar.setOnClickListener {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(R.string.msgSubiendo)
            binding.msgMensaje.visibility = TextView.VISIBLE
            subirAudio()
        }

        //Audio
        audioUtiles = AudioUtiles(requireActivity(), requireContext(), binding.btAccion,binding.btPlay, binding.btDelete,
            getString(R.string.msgInicioNotaAudio), getString(R.string.msgDetieneNotaAudio))

        //Fotos
        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){ result ->
            if(result.resultCode == Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }
        }

        imagenUtiles = ImagenUtiles(requireContext(), binding.btPhoto, binding.btRotaL, binding.btRotaR, binding.imagen, tomarFotoActivity)

        return binding.root
    }

    private fun subirAudio(){
        val audioFile = audioUtiles.audioFile
        if(audioFile.exists() && audioFile.isFile && audioFile.canRead()){
            val ruta = Uri.fromFile(audioFile)
            val rutaNube = "lugaresV/${Firebase.auth.currentUser?.email}/audios/${audioFile.name}"
            val referencia:StorageReference = Firebase.storage.reference.child(rutaNube)
            referencia.putFile(ruta)
                .addOnSuccessListener{
                    referencia.downloadUrl
                        .addOnSuccessListener{
                            val rutaAudio = it.toString()
                            subirImagen(rutaAudio)
                        }
                }
                .addOnCanceledListener{subirImagen("")}
        }
        else{
            subirImagen("")
        }
    }

    private fun subirImagen(rutaAudio:String){
        val imagenFile = imagenUtiles.imagenFile
        if(imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()){
            val ruta = Uri.fromFile(imagenFile)
            val rutaNube = "lugaresV/${Firebase.auth.currentUser?.email}/audios/${imagenFile.name}"
            val referencia:StorageReference = Firebase.storage.reference.child(rutaNube)
            referencia.putFile(ruta)
                .addOnSuccessListener{
                    referencia.downloadUrl
                        .addOnSuccessListener{
                            val rutaImagen = it.toString()
                            addLugar(rutaAudio, rutaImagen)
                        }
                }
                .addOnCanceledListener{addLugar(rutaAudio,"")}
        }
        else{
           addLugar(rutaAudio, "")
        }
    }
    private fun addLugar(rutaAudio: String, rutaImagen:String) {
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()
        if (nombre.isNotEmpty()) { //Si puedo crear un lugar
            val lugar= Lugar("",nombre,correo,telefono,web, rutaAudio, rutaImagen)
            lugarViewModel.addLugar(lugar)
            Toast.makeText(requireContext(),"Lugar Agregado",Toast.LENGTH_SHORT).show()
            //Para devolvernos al fragment addLugar
        } else {  //Mensaje de error...
            Toast.makeText(requireContext(),"Faltan Datos",Toast.LENGTH_SHORT).show()
        }
        findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}