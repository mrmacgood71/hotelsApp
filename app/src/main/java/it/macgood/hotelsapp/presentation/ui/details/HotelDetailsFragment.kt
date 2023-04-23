package it.macgood.hotelsapp.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.fragment.BaseFragment
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.R
import it.macgood.hotelsapp.databinding.FragmentHotelDetailsBinding
import it.macgood.hotelsapp.presentation.viewmodel.HotelViewModel


@AndroidEntryPoint
class HotelDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentHotelDetailsBinding
    private lateinit var mapView: MapView
    private val hotelViewModel: HotelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHotelDetailsBinding.inflate(inflater, container, false)

        val hotelId = arguments?.getString("hotelId") ?: ""

        hotelViewModel.getHotelDescription(hotelId)
        mapView = binding.mapview
        hotelViewModel.hotel.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { hotelDescription ->
                        with(binding) {

                            hotelNameTextView.text = hotelDescription.name
                            hotelAddressTextView.text = hotelDescription.address
                            hotelRatingBar.rating = hotelDescription.stars.toFloat()
                            distanceTextView.text = hotelDescription.distance.toString()
                            suitesAvailabilityTextView.text =
                                countAvailable(hotelDescription.suitesAvailability)

                            mapView.map.move(
                                CameraPosition(
                                    Point(
                                        hotelDescription.latitude,
                                        hotelDescription.longitude
                                    ), 16.0f, 0.0f, 0.0f
                                ),
                                Animation(Animation.Type.SMOOTH, 1.5f),
                                null
                            )
                            Glide.with(requireContext())
                                .load(imageUrl + hotelDescription.image)
                                .error(R.drawable.file_not_found_ver_2)
                                .into(hotelImageView)
                        }
                    }
                }
                is Resource.Loading -> {
                    binding.descriptionProgressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    makeToast(response.message)
                }
            }
        }

        return binding.root
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    companion object {
        const val imageUrl = "https://github.com/iMofas/ios-android-test/raw/master/"
    }
}