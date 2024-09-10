package com.example.baseproyect.data.interceptor

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.io.IOException

internal class HttpError(json: String) {

    @SerializedName("error")
    var error: String? = null

    init {
        val gson = Gson()
        val temp = gson.fromJson(json, HttpError::class.java)
        error = if (temp != null) temp.error else ""
    }

    companion object {

        fun parseException(exception: HttpException): HttpError? {
            return try {
                HttpError(exception.response()!!.errorBody()!!.source().readUtf8())
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun toString(): String {
        return "{error : $error}"
    }
}