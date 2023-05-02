package it.macgood.hotelsapp.presentation.ui.hotellist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.macgood.hotelsapp.R
import it.macgood.hotelsapp.databinding.ItemHotelBinding
import it.macgood.hotelsapp.domain.entyties.Hotel
import it.macgood.hotelsapp.presentation.utils.configSuitesAvailabilityExplainLegend

typealias onClickItemTransition = (ItemHotelBinding) -> Unit
class HotelAdapter(
    private val transitionEvent: onClickItemTransition
) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HotelViewHolder(ItemHotelBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = differ.currentList[position]
        with(holder.binding) {
            hotelNameTextView.text = hotel.name
            hotelAddressTextView.text = hotel.address
            hotelRatingBar.rating = hotel.stars.toFloat()
            distanceTextView.text = hotel.distance.toString()

            configSuitesAvailabilityExplainLegend(
                suitesAvailabilityTextView,
                suitesAvailabilityExplainLegendTextView,
                hotel.suitesAvailability
            )

            suitesAvailabilityTextView.text = hotel.suitesAvailability
            hotelCardView.apply { transitionName = "hotel $position" }
        }

        holder.apply {
            itemView.setOnClickListener {
                val extras: FragmentNavigator.Extras = FragmentNavigatorExtras(
                    binding.hotelCardView to holder.binding.hotelCardView.transitionName
                )

                val bundle = bundleOf(
                    "hotelId" to hotel.id.toString(),
                    "transition" to binding.hotelCardView.transitionName
                )

                itemView.findNavController().navigate(
                    R.id.action_hotelListFragment_to_hotelDetailsFragment,
                    bundle, null, extras
                )
                transitionEvent(binding)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    class HotelViewHolder(val binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root)
}