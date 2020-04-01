package br.com.hussan.covid19.domain

data class CityCases(
    val city: String,
    val cityIbgeCode: String,
    val confirmed: Int,
    val confirmedPer100kInhabitants: Double,
    val date: String,
    val deathRate: Any,
    val deaths: Int,
    val estimatedPopulation2019: Int,
    val isLast: Boolean,
    val orderForOlace: Int,
    val placeType: String,
    val state: String
)
