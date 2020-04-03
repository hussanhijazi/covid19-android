package br.com.hussan.covid19.usecases.di

import br.com.hussan.covid19.usecases.GetCityCases
import br.com.hussan.covid19.usecases.GetCountryCases
import br.com.hussan.covid19.usecases.GetCountryHistoryCases
import org.koin.dsl.module

val useCaseModule = module {
    single { GetCityCases(get()) }
    single { GetCountryCases(get()) }
    single { GetCountryHistoryCases(get()) }
}
