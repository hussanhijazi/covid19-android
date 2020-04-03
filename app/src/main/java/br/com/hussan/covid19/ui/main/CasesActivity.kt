package br.com.hussan.covid19.ui.main

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import br.com.hussan.covid19.R
import br.com.hussan.covid19.domain.CityCasesResult
import br.com.hussan.covid19.domain.CountryCases
import br.com.hussan.covid19.domain.CountryHistoryCases
import br.com.hussan.covid19.extensions.add
import br.com.hussan.covid19.extensions.hide
import br.com.hussan.covid19.extensions.hideKeyboard
import br.com.hussan.covid19.extensions.show
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.hbb20.CountryCodePicker.OnCountryChangeListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cases.*
import kotlinx.android.synthetic.main.lyt_city.view.*
import kotlinx.android.synthetic.main.lyt_city.view.txtCases
import kotlinx.android.synthetic.main.lyt_city.view.txtDeaths
import kotlinx.android.synthetic.main.lyt_country.*
import kotlinx.android.synthetic.main.lyt_country.view.*
import kotlinx.android.synthetic.main.lyt_loading.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class CasesActivity : AppCompatActivity(),
    OnMapReadyCallback {

    private lateinit var countryCases: CountryCases
    private lateinit var cityCases: CityCasesResult
    private lateinit var countryHistory: CountryHistoryCases
    private var googleMap: GoogleMap? = null
    private val viewModel: CasesViewModel by stateViewModel()
    private val compositeDisposable = CompositeDisposable()
    private val country = "BR"
    private val city = "Brasília"

    private val countryToFix = hashMapOf("US" to "USA")

    private val chartCases by lazy { ChartCases(this) }
    private val mapCases by lazy { MapCases(this, this) }

    companion object {
        const val MAPVIEW_BUNDLE_KEY = "MAPVIEW_BUNDLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cases)

        lifecycle.addObserver(MyLifecycle(mapView))

        configureViews()

        mapCases.configureMap(savedInstanceState, mapView)

        if (savedInstanceState == null)
            countrySpinner.setCountryForNameCode(country)
        else {
            val (countryCases, countryHistory, cityCases) = viewModel.getSavedStates()
            countryCases?.let {
                showCountryCases((it to countryHistory) as Pair<CountryCases, CountryHistoryCases>)
            }
            cityCases?.let { showCityCases(it) }
        }
    }

    private fun getCountryData(country: String) {
        viewModel.getCountryData(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doOnError { showLoading(false) }
            .subscribe(::showCountryCases, ::showError)
            .add(compositeDisposable)
    }

    private fun getCityCases(query: String) {
        viewModel.getCityCases(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
            .doOnError { showLoading(false) }
            .subscribe(::showCityCases, ::showError)
            .add(compositeDisposable)
    }

    private fun showCountryCases(countryData: Pair<CountryCases, CountryHistoryCases>) {
        val (countryCases, countryHistory) = countryData
        this.countryCases = countryCases
        this.countryHistory = countryHistory

        lytCountry.txtCases.text = countryCases.cases.toString()
        lytCountry.txtDeaths.text = countryCases.deaths.toString()
        lytCountry.txtCasesConfirmedCountry.text = getString(
            R.string.cases_per_milion,
            countryCases.casesPerOneMillion.toString()
        )

        mapCases.addMarker(googleMap, countryCases, countrySpinner.selectedCountryName)

        chartCases.drawChart(countryHistory.timeline.cases, casesChart)
        chartCases.drawChart(countryHistory.timeline.deaths, deathsChart)
        chartCases.drawChart(countryHistory.timeline.recovered, recoveredChart)

        showLoading(false)
    }

    private fun showCityCases(cityCases: CityCasesResult) {
        this.cityCases = cityCases
        val cityCase = cityCases.results.first()

        lytCity.txtCity.text = cityCase.city
        lytCity.txtCases.text = cityCase.confirmed.toString()

        lytCity.txtDeaths.text = cityCase.deaths.toString()
        lytCity.txtCasesConfirmed.text =
            getString(
                R.string.cases_per,
                String.format("%.2f", cityCase.confirmedPer100kInhabitants)
            )

    }

    private fun configureViews() {
        lytCity.edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getCityCases(v.text.toString())
                hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })

        countrySpinner.setOnCountryChangeListener(OnCountryChangeListener {
            var countryName = countrySpinner.selectedCountryEnglishName
            val countryToFix = countryToFix[countrySpinner.selectedCountryNameCode]
            countryToFix?.let { countryName = countryToFix }
            getCountryData(countryName)
            if (countrySpinner.selectedCountryNameCode == country) {
                lytCity.show()
                getCityCases(city)
            } else lytCity.hide()
        })
    }

    private fun showError(error: Throwable) {
    }

    private fun showLoading(show: Boolean) {
        if (show)
            progressBar.show()
        else
            progressBar.hide()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView.onSaveInstanceState(mapViewBundle)
        viewModel.saveStates(countryCases, countryHistory, cityCases)

    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        googleMap?.setInfoWindowAdapter(CustomInfoWindowAdapter(layoutInflater))

    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
