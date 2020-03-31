package br.com.hussan.covid19.data.datasource

import br.com.hussan.covid19.data.AppApi
import br.com.hussan.covid19.domain.CityCasesResult
import br.com.hussan.covid19.domain.CountryCases
import io.reactivex.Observable

class CaseRepository(
    private val api: AppApi
) : CaseDatasource {
    override fun getCases(query: String): Observable<CityCasesResult> {
        return api.getCityCases(query)
    }

    override fun getCountryCases(country: String): Observable<CountryCases> {
        return api.getCountryCases(country = country)

    }
}

interface CaseDatasource {
    fun getCases(query: String): Observable<CityCasesResult>
    fun getCountryCases(country: String): Observable<CountryCases>
}
