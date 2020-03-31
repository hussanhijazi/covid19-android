package br.com.hussan.covid19.ui.main

import androidx.lifecycle.ViewModel
import br.com.hussan.covid19.domain.CityCasesResult
import br.com.hussan.covid19.domain.CountryCases
import br.com.hussan.covid19.usecases.GetCityCases
import br.com.hussan.covid19.usecases.GetCountryCases
import io.reactivex.Observable

class CasesViewModel(
    private val getCityCasesCase: GetCityCases,
    private val getCountryCases: GetCountryCases
) : ViewModel() {


    fun getCityCases(query: String): Observable<CityCasesResult> = getCityCasesCase.invoke(query)
    fun getCountryCases(country: String): Observable<CountryCases> = getCountryCases.invoke(country)


}

