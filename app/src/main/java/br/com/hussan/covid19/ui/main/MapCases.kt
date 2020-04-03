package br.com.hussan.covid19.ui.main

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
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
    private val onReadyMapReadyCallback: OnMapReadyCallback
) {

    private val geoCoder by lazy { Geocoder(context) }

    fun addMarker(googleMap: GoogleMap?, country: CountryCases, countryName: String) {
        if (googleMap != null) {
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

        }
    }

    fun configureMap(savedInstanceState: Bundle?, mapView: MapView) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(CasesActivity.MAPVIEW_BUNDLE_KEY)
        }
        mapView.onCreate(mapViewBundle)

        mapView.getMapAsync(onReadyMapReadyCallback)
    }

}
