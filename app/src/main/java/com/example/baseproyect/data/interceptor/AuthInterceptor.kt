package com.example.baseproyect.data.interceptor

import android.content.Context
import android.util.Log
import com.example.baseproyect.BuildConfig.BASE_URL
import com.example.baseproyect.R
import com.example.baseproyect.data.dataStore.TokenLocalDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context ,
    private val localDataSource: TokenLocalDataSource

    ): Interceptor {

        private var client: OkHttpClient? = null

    override fun intercept(chain: Interceptor.Chain): Response {
      val token = runBlocking { localDataSource.getToken() }
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $token")

        val request = requestBuilder.build()
        val response: Response = chain.proceed(request)

        //Instantiate Logging interceptor
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        //Let's finish last workday started before starting a new one
        //Instantiate Http client to use
        client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context, localDataSource))
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        when (response.code) {
            401 -> {
                val body1 = response.body!!.source().readUtf8()
                val error = HttpError(body1)

                if (error.error != null) {
                    if ( error.error.toString().equals(
                            context.resources.getString(R.string.token_expired),
                            true
                        )
                    ) {
                        val request1 = Request.Builder()
                            .url(BASE_URL + "refresh")
                            .build()
                        val response1 = client!!.newCall(request1).execute()
                        try {
                            val json = JSONObject(response1.body!!.source().readUtf8())
                            val newToken = json.getString("token")
                            runBlocking {
                                Log.i("TOKEN", "New $newToken")
                                localDataSource.saveToken(json.getString("token"))
                            }

                            val original1 = chain.request()
                            val builder1 = original1.newBuilder()
                            val request2 = builder1.build()
                            return client!!.newCall(request2).execute()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else
                        return response.newBuilder()
                            .body(ResponseBody.create(response.body!!.contentType(), body1))
                            .code(401).build()
                } else
                    return response.newBuilder()
                        .body(ResponseBody.create(response.body!!.contentType(), body1))
                        .code(401).build()
            }
            422 -> {
                val body1 = response.body!!.source().readUtf8()

                return response.newBuilder()
                    .body(body1.toResponseBody(response.body!!.contentType()))
                    .code(422).build()

            }
            else -> return response
        }

        return response
    }
}