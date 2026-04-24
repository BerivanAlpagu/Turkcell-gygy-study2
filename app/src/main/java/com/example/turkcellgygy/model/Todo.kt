package com.example.turkcellgygy.model

import kotlinx.serialization.Serializable

@Serializable
//eğer sadece data taşıyacaksa data yaz classın başına
data class Todo(
    val id: Int,
    val title: String,
    val description: String? = null) {

}