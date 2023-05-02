package it.macgood.hotelsapp.presentation.ui.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.transition.MaterialContainerTransform
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.fragment.BaseFragment
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.R
import it.macgood.hotelsapp.databinding.FragmentHotelDetailsBinding
import it.macgood.hotelsapp.presentation.utils.Constants.BASE_IMAGE_URL
import it.macgood.hotelsapp.presentation.utils.configSuitesAvailabilityExplainLegend
import it.macgood.hotelsapp.presentation.utils.countAvailable

@AndroidEntryPoint
class HotelDetailsFragment : BaseFragment() {

    private val hotelViewModel: HotelDetailsViewModel by viewModels()
    private lateinit var binding: FragmentHotelDetailsBinding

    @SuppressLint("RestrictedApi")
    /**
        MaterialContainerTransform can throw error
        @throws java.io.IOException: null InputStream
        @throws RuntimeException: with maps, smth like: "set api key before initialize()" (Huawei JSN-L21)
        If you have, just delete MaterialContainerTransform
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transformation = MaterialContainerTransform()
        transformation.interpolator = AnimationUtils.LINEAR_INTERPOLATOR
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment_container
            duration = 500
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelDetailsBinding.inflate(inflater, container, false)

        val hotelId = arguments?.getString("hotelId") ?: ""

        hotelViewModel.getHotelDescription(hotelId)
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

                            configSuitesAvailabilityExplainLegend(
                                suitesAvailabilityTextView,
                                suitesAvailabilityExplainLegendTextView,
                                suitesAvailabilityTextView.text.toString()
                            )

                            mapview.map.move(
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
                                .load(BASE_IMAGE_URL + hotelDescription.image)
                                .error(R.drawable.picture_file_not_found_ver_2)
                                .into(hotelImageView).also {
                                    descriptionProgressBar.visibility = View.GONE
                                }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = arguments?.getString("transition") ?: ""
        with(binding.detailsCardView) {
            this.transitionName = key
        }
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }
}