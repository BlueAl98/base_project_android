package com.example.baseproyect.data.remote.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.baseproyect.BuildConfig.BASE_URL
import com.example.baseproyect.data.dataStore.TokenLocalDataSource
import com.example.baseproyect.data.interceptor.AuthInterceptor
import com.example.baseproyect.data.remote.NeworkApi
import com.example.baseproyect.data.repository.LoginRepositoryImpl
import com.example.baseproyect.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideTokenLocalDataSource(
        dataStore: DataStore<Preferences>
    ): TokenLocalDataSource {
        return TokenLocalDataSource(dataStore)
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, tokenLocalDataSource: TokenLocalDataSource ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context, tokenLocalDataSource ))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): NeworkApi {
        return retrofit.create(NeworkApi::class.java)
    }


    @Provides
    @Singleton
    fun provideLoginRepository(api: NeworkApi, tokenLocalDataSource: TokenLocalDataSource): LoginRepository {
        return LoginRepositoryImpl(api, tokenLocalDataSource)
    }


}