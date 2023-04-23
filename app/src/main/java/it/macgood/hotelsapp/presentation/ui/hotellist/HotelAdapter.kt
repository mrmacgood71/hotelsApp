package it.macgood.hotelsapp.presentation.ui.hotellist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.Hotel
import it.macgood.hotelsapp.R
import it.macgood.hotelsapp.databinding.ItemHotelBinding

class HotelAdapter : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

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
            configSuitesAvailabilityExplainLegend(hotel.suitesAvailability)
            suitesAvailabilityTextView.text = hotel.suitesAvailability
        }

        holder.itemView.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(
                    R.id.action_hotelListFragment_to_hotelDetailsFragment,
                    bundleOf("hotelId" to hotel.id.toString())
                )
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private fun ItemHotelBinding.configSuitesAvailabilityExplainLegend(legend: String) {
        if (legend.toInt() < 10) {
            this.suitesAvailabilityTextView.setTextColor(Color.RED)
            this.suitesAvailabilityExplainLegendTextView.setTextColor(Color.RED)
        }
        if (legend.endsWith("1")) {
            this.suitesAvailabilityExplainLegendTextView.text = "место осталось"
        } else if (legend.endsWith("2") || legend.endsWith("3") || legend.endsWith("3")) {
            this.suitesAvailabilityExplainLegendTextView.text = "места осталось"
        } else {
            this.suitesAvailabilityExplainLegendTextView.text = "мест осталось"
        }
    }

    inner class HotelViewHolder(val binding: ItemHotelBinding)
        : RecyclerView.ViewHolder(binding.root)
}