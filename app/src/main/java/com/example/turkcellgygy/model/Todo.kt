package com.example.turkcellgygy.model


//eğer sadece data taşıyacaksa data yaz classın başına
data class Todo(val userId: Int, val id: Int, val title: String, val completed: Boolean) { }