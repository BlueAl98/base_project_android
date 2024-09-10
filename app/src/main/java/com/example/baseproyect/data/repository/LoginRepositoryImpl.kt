package com.example.baseproyect.data.repository

import android.util.Log
import com.example.baseproyect.data.dataStore.TokenLocalDataSource
import com.example.baseproyect.data.remote.NeworkApi
import com.example.baseproyect.domain.model.Login
import com.example.baseproyect.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val api : NeworkApi,
    private val localDataSource: TokenLocalDataSource
): LoginRepository {
    override suspend fun login(username: String, password: String): Login {
        return api.login(username, password).toDomain()
    }

    override suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }

    override suspend fun getToken(): String? {
       return localDataSource.getToken()
    }

    override suspend fun deleteToken() {
        localDataSource.deleteToken()
    }


}