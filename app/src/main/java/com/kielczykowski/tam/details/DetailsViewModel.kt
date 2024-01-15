package com.kielczykowski.tam.details
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kielczykowski.tam.UIStateDetails
import com.kielczykowski.tam.data.citiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val citiessRepository: citiesRepository
): ViewModel() {

    //private val citiessRepository = citiesRepository()

    private val liveData = MutableLiveData<UIStateDetails>()//cities????
    val immutableliveData: LiveData<UIStateDetails> = liveData

    fun loadDetailData(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = citiessRepository.getcitiesDetails(city)
                Log.d(
                    "Details",
                    "getcititesDetails response: ${request.code()}, ${request.isSuccessful}"
                )
//                if (request.isSuccessful) {
//                    val citiess = request.body()
//                    liveData.postValue(citiess)
//                }
                if (request.isSuccessful) {
                    val citiessdetails = request.body()?.data?.populationCounts
                    liveData.postValue(UIStateDetails(citiessdetails = citiessdetails))
                }else{
                    liveData.postValue(UIStateDetails(error = "Fail, code: ${request.code()}"))
                }
            } catch (e: Exception) {
                Log.e("Main", "Błąd!", e)
            }
        }


    }
}

