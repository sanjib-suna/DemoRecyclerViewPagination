package com.example.practicaldemo.api

class Params {
    private val params:HashMap<String,String> = HashMap()
     fun set(key:String,value:String) :HashMap<String,String>{
         params.set(key,value)
         return params
     }
}