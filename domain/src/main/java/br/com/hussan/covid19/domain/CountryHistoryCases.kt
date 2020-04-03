package br.com.hussan.covid19.domain

import java.io.Serializable


data class CountryHistoryCases(
    val country: String,
    val provinces: List<Any>,
    val timeline: Timeline
) : Serializable
