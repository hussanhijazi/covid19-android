package br.com.hussan.covid19.usecases

import br.com.hussan.covid19.data.datasource.CaseDatasource
import br.com.hussan.covid19.domain.CountryCases
import io.reactivex.Observable

class GetCountryCases(private val dataSource: CaseDatasource) {
    operator fun invoke(country: String): Observable<CountryCases> {
        return dataSource.getCountryCases(country)
    }
}

