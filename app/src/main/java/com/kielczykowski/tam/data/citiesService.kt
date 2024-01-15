package com.kielczykowski.tam.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface citiesService {

    @GET("/api/v0.1/countries/population/cities")
    suspend fun getcitiesResponse(): Response<CitiesResponse>


    @POST("/api/v0.1/countries/population/cities")
    suspend fun getcitiesDetails(@Body citiesRequest: CitiesRequest): Response<SingleCityResponse>
    //abstract fun getcitiesResponse(): Response<citiesResponse>

    companion object {
        private const val CITY_URL = "https://countriesnow.space/"

        val logger = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val OkHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(logger)
                .readTimeout(30, TimeUnit.SECONDS)
        }.build()

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(CITY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(OkHttpClient()) // opcja dla autoryzacji
                .client(OkHttp)
                .build()
        }

        val citesServicee: citiesService by lazy {
            retrofit.create(citiesService::class.java)
        }
    }
}