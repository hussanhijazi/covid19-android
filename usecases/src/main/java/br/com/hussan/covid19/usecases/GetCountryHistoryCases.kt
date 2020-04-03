package br.com.hussan.covid19.usecases

import br.com.hussan.covid19.data.datasource.CaseDatasource
import br.com.hussan.covid19.domain.CountryHistoryCases
import io.reactivex.Observable

class GetCountryHistoryCases(private val dataSource: CaseDatasource) {
    operator fun invoke(country: String): Observable<CountryHistoryCases> {
        return dataSource.getCountryHistoryCases(country)
    }
}

