package it.macgood.hotelsapp.presentation.utils

import android.graphics.Color
import android.widget.TextView
import it.macgood.core.fragment.BaseFragment

fun configSuitesAvailabilityExplainLegend(
    suitesAvailabilityTextView: TextView,
    suitesAvailabilityExplainLegendTextView: TextView,
    suitesCount: String
) {
    if (suitesCount.toInt() < 10) {
        suitesAvailabilityTextView.setTextColor(Color.RED)
        suitesAvailabilityExplainLegendTextView.setTextColor(Color.RED)
    }
    if (suitesCount.endsWith("1")) {
        suitesAvailabilityExplainLegendTextView.text = "место осталось"
    } else if (suitesCount.endsWith("2")
        || suitesCount.endsWith("3")
        || suitesCount.endsWith("3")
    ) {
        suitesAvailabilityExplainLegendTextView.text = "места осталось"
    } else {
        suitesAvailabilityExplainLegendTextView.text = "мест осталось"
    }
}

fun BaseFragment.countAvailable(suitesAvailability: String) : String {
    var count = 0
    suitesAvailability.split(":").forEach{
        if (it.isNotBlank()) count += it.toInt()
    }
    return count.toString()
}