package com.example.lugares.repository

import com.example.lugares.data.LugarDao
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lugares.model.Lugar


class LugarRepository(private val lugarDao: LugarDao) {

    fun addLugar(lugar: Lugar){
        lugarDao.addLugar(lugar)
    }

    fun deleteLugar(lugar: Lugar){
        lugarDao.deleteLugar(lugar)
    }

    val getAllData: MutableLiveData<List<Lugar>> = lugarDao.getAllData()

}