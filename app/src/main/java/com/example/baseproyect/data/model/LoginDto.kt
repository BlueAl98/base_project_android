package com.example.baseproyect.data.model

import com.example.baseproyect.domain.model.Login

data class LoginDto(
    val token: String
){
    fun toDomain(): Login {
        return Login(
         token= token
        )
    }
}