package com.sample.myapplication

data class Comics(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Item>?,
    val returned: Int?
)