package com.example.lugares.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.lugares.data.LugarDataBase
import com.example.lugares.model.Lugar
import com.example.lugares.repository.LugarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application) {
    val getAllData: LiveData<List<Lugar>>

    private val repository: LugarRepository

    init{
        val lugarDao = LugarDataBase.getDatabase(application).lugarDao()
        repository = LugarRepository(lugarDao)
        getAllData = repository.getAllData
    }

    fun addLugar(lugar: Lugar){
        viewModelScope.launch(Dispatchers.IO){repository.addLugar(lugar)}
    }

    fun updateLugar(lugar: Lugar){
        viewModelScope.launch (Dispatchers.IO){ repository.updateLugar(lugar)  }
    }

    fun delelteLugar(lugar: Lugar){
        viewModelScope.launch (Dispatchers.IO){ repository.deleteLugar(lugar) }
    }

}