package br.com.hussan.covid19.ui.main

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import br.com.hussan.covid19.R
import br.com.hussan.covid19.extensions.textHtml
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(layoutInflater: LayoutInflater) :
    GoogleMap.InfoWindowAdapter {


    private val contents: View = layoutInflater.inflate(R.layout.custom_info_contents, null)

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        render(marker, contents)
        return contents
    }

    private fun render(marker: Marker, view: View) {
        val title: String? = marker.snippet
        val titleUi = view.findViewById<TextView>(R.id.title)
        title?.let {
            titleUi.textHtml(it)
        }
    }
}
