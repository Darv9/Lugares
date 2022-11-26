package com.example.lugares.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lugares.model.Lugar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class LugarDao {
     //Firebase Vars
     private var codigoUsuario: String
     private var firestore: FirebaseFirestore

     init {
         codigoUsuario = Firebase.auth.currentUser?.email.toString()
         firestore = FirebaseFirestore.getInstance()
         firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
     }

    fun getAllData(): MutableLiveData<List<Lugar>>{
        val listaLugares = MutableLiveData<List<Lugar>>()
        firestore
            .collection("lugaresViernes")
            .document(codigoUsuario)
            .collection("misLugares")
            .document()
            .addSnapshotListener{ snapshot, e ->
                if(e!=null){
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val lista = ArrayList<Lugar>()
                    val lugares = snapshot.documents
                    lugares.forEach{
                        val lugar = it.toObject(Lugar::class.java)
                        if(lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }
            }
        return listaLugares
    }

    fun addLugar(lugar: Lugar){
        val document: DocumentReference
        if(lugar.id.isEmpty()){
            //Agregar
            document = firestore
                .collection("lugaresViernes")
                .document(codigoUsuario)
                .collection("misLugares")
                .document()
            lugar.id = document.id
        }else{
            //Modificar
            document = firestore
                .collection("lugarViernes")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
        }
        document.set(lugar)
            .addOnCompleteListener{
                Log.d("guardarLugar","Guardado con exito")
            }
            .addOnCompleteListener{
                Log.e("guardarLugar","Error al guardar")
            }
    }

    fun deleteLugar(lugar:Lugar){
        if(lugar.id.isNotEmpty()){
            firestore
                .collection("lugarViernes")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
                .delete()
                .addOnCompleteListener{
                    Log.d("eliminarLugar","Eliminado con exito")
                }
                .addOnCanceledListener {
                    Log.e("eliminarLugar", "Error al eliminar")
                }
        }
    }


}