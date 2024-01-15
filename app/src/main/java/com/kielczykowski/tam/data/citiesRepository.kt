package com.kielczykowski.tam.data
import retrofit2.Response
import kotlin.String

class citiesRepository {

    suspend fun getcitiesResponse(): Response<CitiesResponse> = citiesService.citesServicee.getcitiesResponse()

    suspend fun getcitiesDetails(city: String): Response<SingleCityResponse> =
        citiesService.citesServicee.getcitiesDetails(citiesRequest = CitiesRequest(city))
}