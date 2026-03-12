package com.pratik.home_listing_feature.data.remote

import com.pratik.home_listing_feature.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiServicesImpl(private val client: HttpClient) :
    ApiServices {
    override suspend fun getAllPost(category: String): com.pratik.home_listing_feature.data.model.NewsListDto {
        return client.get(Constants.BASE_URL + "top-headlines/category/${category}/in.json").body()
    }
}
