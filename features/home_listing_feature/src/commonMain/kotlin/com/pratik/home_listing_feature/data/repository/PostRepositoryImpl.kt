package com.pratik.home_listing_feature.data.repository

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.pratik.demoapp.core.utils.NewsList
import com.pratik.demoapp.db.AppDatabase
import com.pratik.home_listing_feature.data.model.toNewsList
import com.pratik.home_listing_feature.data.remote.ApiServices
import com.pratik.home_listing_feature.domain.repository.PostRepository
import com.pratik.home_listing_feature.utils.PlatformChecker

class PostRepositoryImpl(
    private val apiService: ApiServices,
    private val database: AppDatabase,
    private val platform: PlatformChecker
) : PostRepository {

    override suspend fun getAllPost(category: String): List<NewsList> {
        val response = apiService.getAllPost(category)
        val articles = response.articles

        val favoriteUrls = if (platform.getPlatform() == "ANDROID") {
            try {
                database.newsQueries
                    .getAllFavorites()
                    .awaitAsList()
                    .map { it.url }
                    .toSet()
            } catch (e: Exception) {
                emptySet()
            }
        } else {
            emptySet()
        }

        return articles.map { article ->
            val news = article.toNewsList()
            news.copy(isFavorite = favoriteUrls.contains(news.url))
        }
    }

    override suspend fun saveNews(category: String, newsList: List<NewsList>) {
        if (platform.getPlatform() == "ANDROID") {
            database.newsQueries.transaction {
                newsList.forEach { news ->
                    database.newsQueries.insertNews(
                        url = news.url,
                        name = news.name,
                        author = news.author,
                        title = news.title,
                        description = news.description,
                        urlToImage = news.urlToImage,
                        publishedAt = news.publishedAt,
                        category = category,
                        isFavorite = news.isFavorite
                    )
                }
            }
        }
    }

    override suspend fun updateFavorite(url: String, isFavorite: Boolean) {
        if (platform.getPlatform() == "ANDROID") {
            database.newsQueries.updateFavorite(isFavorite, url)
        }
    }

    override suspend fun getSavedNews(category: String): List<NewsList> {
        return if (platform.getPlatform() == "ANDROID") {
            database.newsQueries
                .getNewsByCategory(category)
                .awaitAsList()
                .map {
                    NewsList(
                        name = it.name,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        isFavorite = it.isFavorite ?: false
                    )
                }
        } else {
            emptyList()
        }
    }

    override suspend fun getAllFavorites(): List<NewsList> {
        return if (platform.getPlatform() == "ANDROID") {
            database.newsQueries
                .getAllFavorites()
                .awaitAsList()
                .map {
                    NewsList(
                        name = it.name,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        isFavorite = it.isFavorite ?: false
                    )
                }
        } else {
            emptyList()
        }
    }
}
