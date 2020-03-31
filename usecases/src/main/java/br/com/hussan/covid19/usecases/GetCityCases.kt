package br.com.hussan.covid19.usecases

import br.com.hussan.covid19.data.datasource.CaseDatasource
import br.com.hussan.covid19.domain.CityCasesResult
import io.reactivex.Observable

class GetCityCases(private val dataSource: CaseDatasource) {
    operator fun invoke(query: String): Observable<CityCasesResult> {
        return dataSource.getCases(query)
    }
}

