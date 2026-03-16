package com.pratik.home_listing_feature.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String? = null,
    val name: String
)