package com.amal.efuturespracticaltest.data.model

data class Cases(
    val id: Int = 0,
    val text: String = "",
    val image: String? = "",
    val answers: List<Answers>?,
) {}