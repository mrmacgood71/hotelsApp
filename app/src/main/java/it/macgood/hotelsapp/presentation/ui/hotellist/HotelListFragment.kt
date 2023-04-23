package it.macgood.hotelsapp.presentation.ui.hotellist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.fragment.BaseFragment
import it.macgood.core.network.Resource
import it.macgood.hotelsapp.databinding.FragmentHotelListBinding
import it.macgood.hotelsapp.presentation.viewmodel.HotelViewModel
import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.SortBy

@AndroidEntryPoint
class HotelListFragment : BaseFragment() {

    private lateinit var binding: FragmentHotelListBinding
    private val hotelsViewModel: HotelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelListBinding.inflate(inflater, container, false)
        val hotelAdapter = HotelAdapter()

        hotelsViewModel.hotelsList.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { hotels ->
                        hotels.forEach { hotel -> hotel.countAvailable(hotel.suitesAvailability) }
                        hotelAdapter.differ.submitList(hotels)
                        binding.loadingHotelsProgressBar.visibility = View.GONE
                    }
                }
                is Resource.Loading -> {
                    binding.loadingHotelsProgressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    makeToast(response.message)
                }
            }
        }
        binding.hotelsRecyclerView.adapter = hotelAdapter

        configFilters()

        return binding.root
    }

    private fun configFilters() {
        binding.defaultFilterRadioButton.isChecked = true
        binding.defaultFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.DEFAULT)
            binding.hotelsRecyclerView.smoothScrollToPosition(0)
        }

        binding.closeToCenterFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.DISTANCE_TO_CENTER_ASC)
            binding.hotelsRecyclerView.smoothScrollToPosition(0)
        }

        binding.fartherFromCenterFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.DISTANCE_TO_CENTER_DESC)
            binding.hotelsRecyclerView.smoothScrollToPosition(0)
        }

        binding.moreSuitesAvailableFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.SUITES_AVAILABLE_DESC)
            binding.hotelsRecyclerView.smoothScrollToPosition(0)
        }

        binding.lessSuitesAvailableFilterRadioButton.setOnClickListener {
            hotelsViewModel.sortHotelsList(SortBy.SUITES_AVAILABLE_ASC)
            binding.hotelsRecyclerView.smoothScrollToPosition(0)
        }
    }
}