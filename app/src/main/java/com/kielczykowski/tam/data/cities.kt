package com.kielczykowski.tam.data

//data class citiesDetails (
//    val data: List<details>
////    val city : String,
////    val country: String,
////    val populationCounts: List<population>,
////    val yearCounts: List<yearcount>
//)

data class CitiesResponse(
    val msg: String,
    val data: List<Cities>,
    //val citydetails: List<details>
    //val city: String
    //val populationCounts: List<population>
)

data class CitiesRequest(
    val city: String
)

data class SingleCityResponse(
    val msg: String,
    val data: Cities
)


data class Cities(
    val city: String,
    val country: String,
    val populationCounts: List<population>,
    val populationObject: population
    //val yearCounts: List<yearcount>
)

//data class details(
//    val city: String,
//    val country: String,
//    val populationCounts: List<population>,
//    val yearCounts: List<yearcount>
//)

data class population(
    val value: String,
    val year: String
)

//data class yearcount(
//    val year: String
//)




//{
//{
//    "error"
//}:false,
//    "msg":"all cities with population",
//    "data":[{"city":"MARIEHAMN",
//    "country":"Åland Islands",
//    "populationCounts":[{"year":"2013",
//        "value":"11370",
//        "sex":"Both Sexes",
//        "reliabilty":"Final figure,
//        complete"}
//}