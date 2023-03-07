package com.sphirye.spring.boot.template.service.tool

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sphirye.spring.boot.template.service.connector.adapter.SimpleGrantedAuthorityCollectionAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.security.core.authority.SimpleGrantedAuthority
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitTool {

    var gson = Gson()

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gsonBuilder = GsonBuilder()
            .registerTypeAdapter(SimpleGrantedAuthority::class.java, SimpleGrantedAuthorityCollectionAdapter())
            .create()

        val gsonConverter = GsonConverterFactory.create(gsonBuilder)

    }
}