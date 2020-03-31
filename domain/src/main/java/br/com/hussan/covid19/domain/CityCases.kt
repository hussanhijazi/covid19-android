package br.com.hussan.covid19.domain

data class CityCases(
    val city: String,
    val city_ibge_code: String,
    val confirmed: Int,
    val confirmed_per_100k_inhabitants: Double,
    val date: String,
    val death_rate: Any,
    val deaths: Int,
    val estimated_population_2019: Int,
    val is_last: Boolean,
    val order_for_place: Int,
    val place_type: String,
    val state: String
)
