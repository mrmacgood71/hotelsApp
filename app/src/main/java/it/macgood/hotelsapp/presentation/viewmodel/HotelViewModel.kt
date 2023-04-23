package it.macgood.hotelsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.Hotel
import it.macgood.hotelsapp.presentation.ui.details.entyties.HotelDescription
import it.macgood.hotelsapp.domain.usecase.GetHotelUseCase
import it.macgood.hotelsapp.domain.usecase.GetHotelsUseCase
import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.SortBy
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor (
    val getHotelsUseCase: GetHotelsUseCase,
    val getHotelUseCase: GetHotelUseCase
) : ViewModel() {

    private val _hotelsList: MutableLiveData<Resource<List<Hotel>>> = MutableLiveData()
    val hotelsList: LiveData<Resource<List<Hotel>>> = _hotelsList


    private val _hotel: MutableLiveData<Resource<HotelDescription>> = MutableLiveData()
    val hotel: LiveData<Resource<HotelDescription>> = _hotel

    init {
        getHotels()
    }

    fun sortHotelsList(sortBy: SortBy) {
        try {
            when (sortBy) {
                SortBy.DEFAULT -> {
                    getHotels()
                }
                SortBy.SUITES_AVAILABLE_ASC -> {
                    _hotelsList.postValue(Resource.Success(_hotelsList.value?.data!!.sortedBy {
                        it.suitesAvailability.toInt()
                    }))
                }
                SortBy.SUITES_AVAILABLE_DESC -> {
                    _hotelsList.postValue(Resource.Success(_hotelsList.value?.data!!.sortedByDescending {
                        it.suitesAvailability.toInt()
                    }))
                }
                SortBy.DISTANCE_TO_CENTER_ASC -> {
                    _hotelsList.postValue(Resource.Success(_hotelsList.value?.data!!.sortedBy {
                        it.distance
                    }))
                }
                SortBy.DISTANCE_TO_CENTER_DESC -> {
                    _hotelsList.postValue(Resource.Success(_hotelsList.value?.data!!.sortedByDescending {
                        it.distance
                    }))
                }
            }

        } catch (e: java.lang.UnsupportedOperationException) {
            Log.d("TAG", "sortByName: UnsupportedOperationException")
        }

    }

    private fun getHotels() = viewModelScope.launch {
        _hotelsList.postValue(Resource.Loading())
        val response = getHotelsUseCase.execute()
        _hotelsList.postValue(handleHotelsResponse(response))
    }

    fun getHotelDescription(id: String) = viewModelScope.launch {
        _hotel.postValue(Resource.Loading())
        val response = getHotelUseCase.execute(id)
        _hotel.postValue(handleHotelResponse(response))
    }


    private fun handleHotelsResponse(response: Response<List<Hotel>>) : Resource<List<Hotel>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleHotelResponse(response: Response<HotelDescription>) : Resource<HotelDescription> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}