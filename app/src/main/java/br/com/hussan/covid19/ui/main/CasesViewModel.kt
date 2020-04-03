package br.com.hussan.covid19.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import br.com.hussan.covid19.domain.CityCasesResult
import br.com.hussan.covid19.domain.CountryCases
import br.com.hussan.covid19.domain.CountryHistoryCases
import br.com.hussan.covid19.usecases.GetCityCases
import br.com.hussan.covid19.usecases.GetCountryCases
import br.com.hussan.covid19.usecases.GetCountryHistoryCases
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class CasesViewModel(
    private val state: SavedStateHandle,
    private val getCityCasesCase: GetCityCases,
    private val getCountryCases: GetCountryCases,
    private val getCountryHistoryCases: GetCountryHistoryCases
) : ViewModel() {

    companion object {
        const val COUNTRY_CASES = "COUNTRY_CASES"
        const val COUNTRY_HISTORY = "COUNTRY_HISTORY"
        const val CITY_CASES = "CITY_CASES"
    }

    fun getCityCases(query: String): Observable<CityCasesResult> = getCityCasesCase.invoke(query)

    fun getCountryData(country: String): Observable<Pair<CountryCases, CountryHistoryCases>> {
        return Observables.zip(
            getCountryCases.invoke(country),
            getCountryHistoryCases.invoke(country)
        ) { countryCases, countryHistory ->
            countryCases to countryHistory
        }
    }

    fun getSavedStates(): Triple<CountryCases?, CountryHistoryCases?, CityCasesResult?> {
        return Triple(
            state.get<CountryCases>(COUNTRY_CASES),
            state.get<CountryHistoryCases>(COUNTRY_HISTORY),
            state.get<CityCasesResult>(CITY_CASES)
        )
    }

    fun saveStates(
        countryCases: CountryCases,
        countryHistory: CountryHistoryCases,
        cityCases: CityCasesResult
    ) {
        state.set(COUNTRY_CASES, countryCases)
        state.set(COUNTRY_HISTORY, countryHistory)
        state.set(CITY_CASES, cityCases)
    }

}

