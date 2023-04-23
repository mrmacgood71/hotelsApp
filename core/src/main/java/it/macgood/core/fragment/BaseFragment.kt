package it.macgood.core.fragment

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun makeToast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun getDrawable(id: Int) : Drawable? {
        return ResourcesCompat.getDrawable(resources, id, requireActivity().theme)
    }

    fun getColor(id: Int): Int {
        return resources.getColor(id, requireActivity().theme)
    }

    fun countAvailable(suitesAvailability: String) : String {
        var count = 0
        suitesAvailability.split(":").forEach{
            if (it.isNotBlank()) count += it.toInt()
        }
        return count.toString()
    }

}