package it.macgood.hotelsapp.presentation.ui.hotellist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.fragment.BaseFragment
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.databinding.FragmentHotelListBinding
import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.SortBy
import it.macgood.hotelsapp.presentation.utils.countAvailable

@AndroidEntryPoint
class HotelListFragment : BaseFragment() {

    private lateinit var binding: FragmentHotelListBinding
    private val hotelsViewModel: HotelListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHotelListBinding.inflate(inflater, container, false)

        val hotelAdapter = HotelAdapter {

            exitTransition = MaterialElevationScale(false).apply {
                duration = 300
            }

            reenterTransition = MaterialElevationScale(true).apply {
                duration = 300
            }
        }

        binding.hotelsRecyclerView.adapter = hotelAdapter

        hotelsViewModel.hotelsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { hotels ->
                        hotels.forEach { hotel ->
                            hotel.suitesAvailability = countAvailable(hotel.suitesAvailability)
                        }
                        hotelAdapter.differ.submitList(hotels)
                        binding.loadingHotelsProgressBar.visibility = View.GONE
                    }
                    binding.shimmerLayout.hideShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                    makeToast(response.message)
                    binding.shimmerLayout.hideShimmer()
                }
            }
        }
        binding.configFilters()

        return binding.root
    }

    private fun FragmentHotelListBinding.configFilters() {
        defaultFilterRadioButton.isChecked = true
        defaultFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.DEFAULT)
            hotelsRecyclerView.smoothScrollToPosition(0)
        }

        closeToCenterFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.DISTANCE_TO_CENTER_ASC)
            hotelsRecyclerView.smoothScrollToPosition(0)
        }

        fartherFromCenterFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.DISTANCE_TO_CENTER_DESC)
            hotelsRecyclerView.smoothScrollToPosition(0)
        }

        moreSuitesAvailableFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.SUITES_AVAILABLE_DESC)
            hotelsRecyclerView.smoothScrollToPosition(0)
        }

        lessSuitesAvailableFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.SUITES_AVAILABLE_ASC)
            hotelsRecyclerView.smoothScrollToPosition(0)
        }
    }
}