package br.com.hussan.covid19.di

import br.com.hussan.covid19.AppNavigator
import br.com.hussan.covid19.ui.main.CasesActivity
import br.com.hussan.covid19.ui.main.CasesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { CasesViewModel(get(), get()) }
    single { (activity: CasesActivity) ->
        AppNavigator(activity = activity)
    }
}
