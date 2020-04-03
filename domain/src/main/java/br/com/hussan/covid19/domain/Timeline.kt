package br.com.hussan.covid19.domain


data class Timeline(
    val cases: LinkedHashMap<String, Integer>,
    val deaths: LinkedHashMap<String, Integer>,
    val recovered: LinkedHashMap<String, Integer>
)
