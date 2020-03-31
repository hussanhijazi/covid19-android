package br.com.hussan.covid19.data.di

import br.com.hussan.covid19.data.datasource.CaseDatasource
import br.com.hussan.covid19.data.datasource.CaseRepository
import org.koin.dsl.module

val dataModule = module {
    single<CaseDatasource> { CaseRepository(get()) }
}
