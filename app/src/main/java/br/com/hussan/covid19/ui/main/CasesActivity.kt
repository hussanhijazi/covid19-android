package br.com.hussan.covid19.ui.main

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import br.com.hussan.covid19.R
import br.com.hussan.covid19.domain.CityCasesResult
import br.com.hussan.covid19.domain.CountryCases
import br.com.hussan.covid19.extensions.add
import br.com.hussan.covid19.extensions.hide
import br.com.hussan.covid19.extensions.hideKeyboard
import br.com.hussan.covid19.extensions.show
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
import org.koin.androidx.viewmodel.ext.android.viewModel


class CasesActivity : AppCompatActivity() {

    private val viewModel: CasesViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private val country = "BR"
    private val city = "Brasília"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cases)

        configureViews()

        countrySpinner.setCountryForNameCode(country)
    }

    private fun getCountryCases(country: String) {
        viewModel.getCountryCases(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading(true) }
            .doFinally { showLoading(false) }
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

    private fun showCountryCases(country: CountryCases) {
        lytCountry.txtCases.text = country.cases.toString()
        lytCountry.txtDeaths.text = country.deaths.toString()
        lytCountry.txtCasesConfirmedCountry.text = getString(
            R.string.cases_per_milion,
            country.casesPerOneMillion.toString()
        )
    }

    private fun showCityCases(cases: CityCasesResult) {
        val cityCase = cases.results.first()

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
            getCountryCases(countrySpinner.selectedCountryEnglishName)
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


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
