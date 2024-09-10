package com.example.baseproyect.domain.repository

import com.example.baseproyect.domain.model.Login

interface LoginRepository {

    suspend fun login(username:String, password:String) :Login

    suspend fun saveToken(token:String)

    suspend fun getToken():String?

    suspend fun deleteToken()

}