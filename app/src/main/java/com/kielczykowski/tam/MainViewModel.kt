package com.kielczykowski.tam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kielczykowski.tam.data.citiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val citiesRepositoryy: citiesRepository
) : ViewModel() {


    //private val citiesRepositoryy = citiesRepository()

    //private val citiesServicee = citiesService.retrofit.create(citiesService::class.java)

    //private suspend fun getcitiesResponse(): Response<citiesResponse> = citiesServicee.getcitiesResponse()

    private val mutablecitiesData = MutableLiveData<UIState>()
    val immutablecitiesData: LiveData<UIState> = mutablecitiesData

    val filterQuery = MutableLiveData("")

    fun updateFilterQuery(text: String){
        filterQuery.postValue(text)
    }

    fun getData() {
        mutablecitiesData.postValue(UIState(isLoading = true))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = citiesRepositoryy.getcitiesResponse()
                if (request.isSuccessful) {
                    val citiess = request.body()?.data
                    mutablecitiesData.postValue(UIState(citiess = citiess))
                }else{
                    mutablecitiesData.postValue(UIState(error = "Fail, code: ${request.code()}"))
                }
        } catch (e: Exception){
            mutablecitiesData.postValue(UIState(error = e.message))
            Log.e("MainViewModel", "Blad!", e)

            }
        }
    }

//    @DrawableRes
//    private fun getRandomImage(): Int{
//        //jaki seed, return
//        var x = 1
//        retrun if (x == 1) {
//            return R.drawable.image1
//        }else{
//            R.drawable.image2
//        }
//    }
}