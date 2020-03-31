package br.com.hussan.covid19.domain

data class CountryCases(
    val active: Int,
    val cases: Int,
    val casesPerOneMillion: Int,
    val country: String,
    val critical: Int,
    val deaths: Int,
    val deathsPerOneMillion: Int,
    val firstCase: String,
    val recovered: Int,
    val todayCases: Int,
    val todayDeaths: Int
)
