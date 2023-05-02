package it.macgood.hotelsapp.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.domain.entyties.HotelDescription
import it.macgood.hotelsapp.domain.usecase.GetHotelUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor(
    private val getHotelUseCase: GetHotelUseCase
) : ViewModel() {

    private val _hotel: MutableLiveData<Resource<HotelDescription>> = MutableLiveData()
    val hotel: LiveData<Resource<HotelDescription>> = _hotel

    fun getHotelDescription(id: String) = viewModelScope.launch {
        _hotel.postValue(Resource.Loading())
        val response = getHotelUseCase.execute(id)
        _hotel.postValue(handleHotelResponse(response))
    }
    private fun handleHotelResponse(response: Response<HotelDescription>): Resource<HotelDescription> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}