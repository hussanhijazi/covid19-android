package br.com.hussan.covid19.data

import br.com.hussan.covid19.domain.CityCasesResult
import br.com.hussan.covid19.domain.CountryCases
import br.com.hussan.covid19.domain.CountryHistoryCases
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://coronavirus-tracker-api.herokuapp.com/
// https://corona.lmao.ninja/v2/historical/brazil
interface AppApi {

    @GET("caso/data?date=&state=&city=&place_type=&is_last=&city_ibge_code=&order_for_place=&is_last=True")
    fun getCityCases(@Query("search") search: String): Observable<CityCasesResult>

    @GET("https://coronavirus-19-api.herokuapp.com/countries/{country}")
    fun getCountryCases(
        @Path("country") country: String
    ): Observable<CountryCases>

    @GET("https://corona.lmao.ninja/v2/historical/{country}")
    fun getCountryHistoryCases(
        @Path("country") country: String
    ): Observable<CountryHistoryCases>
}
