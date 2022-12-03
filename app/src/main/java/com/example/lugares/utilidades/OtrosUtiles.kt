package com.example.lugares.utilidades

import java.text.SimpleDateFormat
import java.util.*

class OtrosUtiles {
    companion object{
        fun getTempFile(prefijo:String):String{
            val nombre = SimpleDateFormat("yyyyMMdd-HHmmSS",Locale.getDefault()).format(Date())
            return prefijo+nombre
        }
    }
}