package it.macgood.hotelsapp.presentation.ui.hotellist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.domain.entyties.Hotel
import it.macgood.hotelsapp.domain.entyties.HotelDescription
import it.macgood.hotelsapp.domain.usecase.GetHotelUseCase
import it.macgood.hotelsapp.domain.usecase.GetHotelsUseCase
import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.SortBy
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HotelListViewModel @Inject constructor(
    private val getHotelsUseCase: GetHotelsUseCase
) : ViewModel() {

    private val _hotelsList: MutableLiveData<Resource<List<Hotel>>> = MutableLiveData()
    val hotelsList: LiveData<Resource<List<Hotel>>> = _hotelsList

    init {
        getHotels()
    }

    fun sortHotelsList(sortBy: SortBy) {

        when (sortBy) {
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
            SortBy.DEFAULT -> {
                getHotels()
            }
        }
    }

    private fun getHotels() = viewModelScope.launch {
        _hotelsList.postValue(Resource.Loading())
        val response = getHotelsUseCase.execute()
        _hotelsList.postValue(handleHotelsResponse(response))
    }

    private fun handleHotelsResponse(response: Response<List<Hotel>>): Resource<List<Hotel>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}