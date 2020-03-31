package br.com.hussan.covid19

import android.app.Application
import br.com.hussan.covid19.data.di.apiModule
import br.com.hussan.covid19.data.di.dataModule
import br.com.hussan.covid19.di.appModule
import br.com.hussan.covid19.usecases.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    appModule,
                    useCaseModule,
                    apiModule,
                    dataModule
                )
            ).androidContext(this@MyApp)
        }
    }
}
