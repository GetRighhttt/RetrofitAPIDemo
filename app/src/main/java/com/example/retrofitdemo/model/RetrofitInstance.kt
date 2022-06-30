package com.example.retrofitdemo.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

/**
 * Here we will demonstrate how we create a Retrofit Instance with the Builder function
 * of the Retrofit class.
 *
 * We use a companion object to return a singleton or single instance of Retrofit.
 *
 * companion objects initialize when the code is loaded in Kotlin, so it's an easy way
 * to get an instance of Retrofit.
 */
class RetrofitInstance {

    companion object {

        /**
         * Here we will create an instance of Interceptor here which is pivotal for showing
         * logs of network operations that happened in our application.
         *
         * They are very useful to understand what is going on.
         *
         * The Body level logs requests and response lines and their respective headers and bodies
         * of the network operation.
         *
         * BASIC - logs requests and response only
         *
         * HEADER - logs requests and responses and HEADERS
         */

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        /**
         * Now we create an OKHTTPClient Instance.
         *
         * And we will show how to manually do connection timeouts just in case
         * somebody has slow internet.
         */
        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()


        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory
                    .create(GsonBuilder().create()))
                .build()
        }
    }
}

