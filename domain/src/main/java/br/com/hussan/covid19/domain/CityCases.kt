package br.com.hussan.covid19.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CityCases(
    val city: String,
    val cityIbgeCode: String,
    val confirmed: Int,
    @SerializedName("confirmed_per_100k_inhabitants")
    val confirmedPer100kInhabitants: Double,
    val date: String,
    val deathRate: Any,
    val deaths: Int,
    val estimatedPopulation2019: Int,
    val isLast: Boolean,
    val orderForOlace: Int,
    val placeType: String,
    val state: String
) : Serializable
