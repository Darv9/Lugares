package com.example.lugares.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lugares.databinding.LugarFilaBinding
import com.example.lugares.model.Lugar
import com.example.lugares.ui.home.HomeFragmentDirections


class LugarAdapter : RecyclerView.Adapter<LugarAdapter.LugarViewHolder>(){
    //Se crea una lista para almacenar la informacion de los lugares
    private var listaLugares = emptyList<Lugar>()
    /*¿Cómo funciona binding?
    View Binding: Esta librería permite acceder a las vistas de una forma muy sencilla,
    enlazando variables de nuestro código Kotlin o Java con los componentes del XML.*/

    inner class LugarViewHolder(private val itemBinding:LugarFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(lugar: Lugar) {
            itemBinding.tvTelefono.text = lugar.telefono
            itemBinding.tvCorreo.text = lugar.correo
            itemBinding.tvNombre.text = lugar.nombre

            if(lugar.rutaImagen?.isNotEmpty() == true){
                Glide.with(itemBinding.root.context)
                    .load(lugar.rutaImagen)
                    .into(itemBinding.imagen)
            }
            //Evento enviar update
            itemBinding.vistaFila.setOnClickListener{
                val accion = HomeFragmentDirections.actionHomeFragmentToUpdateLugarFragment(lugar)
                itemView.findNavController().navigate(accion)
            }
        }
    }
    //Para crear una "vista" de cada fila de lugares que hereda de ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val itemBinding = LugarFilaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return LugarViewHolder(itemBinding)
    }
    //Para "dibujar" la información de cada lugar...
    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        val lugarActual = listaLugares[position]
        holder.bind(lugarActual)
    }
    override fun getItemCount(): Int {
        return listaLugares.size
    }
    fun setData(lugares: List<Lugar>) {
        this.listaLugares = lugares
        notifyDataSetChanged() //Provoca que se redibuje la lista
    }
}