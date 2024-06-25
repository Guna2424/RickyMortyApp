package com.guna.rickymortyapp.Data

data class CharacterData(
    val name: String?,
    val image: String?,
    val status: String?,
    val species: String?,
    val gender: String?,
    val id: String?,
    val location: String?,
    val type: String?,
)

data class CharacterDataList(
    val articles: List<CharacterData>
)
