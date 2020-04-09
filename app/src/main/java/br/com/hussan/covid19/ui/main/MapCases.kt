package br.com.hussan.covid19.ui.main

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.annotation.UiThread
import br.com.hussan.covid19.R
import br.com.hussan.covid19.domain.CountryCases
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapCases(
    private val context: Context,
    private val onReadyMapReadyCallback: OnMapReadyCallback,
    private val mapView: MapView
) {

    private val geoCoder by lazy { Geocoder(context) }

    companion object {
        const val MAPVIEW_BUNDLE_KEY = "MAPVIEW_BUNDLE_KEY"
    }

    fun addMarker(googleMap: GoogleMap?, country: CountryCases, countryName: String) {
        if (googleMap != null) {
            try {
                val p = geoCoder.getFromLocationName(countryName, 10)
                val location = LatLng(p.first().latitude, p.first().longitude)
                googleMap.addMarker(
                    MarkerOptions().position(location)
                        .snippet(
                            context.getString(
                                R.string.cases_deaths_infos,
                                country.cases,
                                country.deaths,
                                country.recovered
                            )
                        )
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5F))
            } catch (e: Exception) {
            }
        }
    }

    fun onSaveInstanceState(outState: Bundle?) {
        var mapViewBundle = outState?.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState?.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    @UiThread
    fun configureMap(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(onReadyMapReadyCallback)
    }

}
