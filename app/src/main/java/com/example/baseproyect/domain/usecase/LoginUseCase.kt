package com.example.baseproyect.domain.usecase

import com.example.baseproyect.domain.exeptions.ResultState
import com.example.baseproyect.domain.model.Login
import com.example.baseproyect.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
  private val repository: LoginRepository

) {

    suspend fun login(username:String, password:String): Flow<ResultState<Login>> = flow {
        try {
            emit(ResultState.Loading)
            val response = repository.login(username, password)
            emit(ResultState.Success(response))

        } catch (e: Throwable) {
            emit(ResultState.Failure(e))
        }
    }

    suspend fun saveToken(token:String){
       repository.saveToken(token)
    }

    suspend fun getToken():String?{
        return  repository.getToken()
    }

    suspend fun deleteToken(){
        repository.deleteToken()
    }
}