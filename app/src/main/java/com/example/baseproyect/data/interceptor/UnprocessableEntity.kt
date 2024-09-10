package com.example.baseproyect.data.interceptor

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.io.IOException

class UnprocessableEntity(json: String) {

    @SerializedName("message")
    var message: String
        internal set
    @SerializedName("errors")
    var errors: List<String>
        internal set

    init {
        val gson = Gson()
        val temp = gson.fromJson(json, UnprocessableEntity::class.java)
        message = temp.message
        errors = temp.errors
    }

    companion object {

        fun parseException(exception: HttpException): UnprocessableEntity? {
            return try {
                UnprocessableEntity(exception.response()!!.errorBody()!!.source().readUtf8().toString())
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun toString(): String {
        return "UnprocessableEntity{" +
                "message='" + message + '\''.toString() +
                ", errors=" + errors +
                '}'.toString()
    }
}