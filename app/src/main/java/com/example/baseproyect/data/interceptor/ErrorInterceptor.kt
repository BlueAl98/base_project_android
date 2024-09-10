package com.example.baseproyect.data.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =  chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful){
            Log.d("ERROR CODE", response.code.toString())
            throw ApiException(response.code, response.message)
        }
        return response

    }

}

class ApiException(val code: Int, message: String) : IOException(message)
