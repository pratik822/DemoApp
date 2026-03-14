package com.pratik.home_listing_feature.data.repository

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.pratik.demoapp.core.utils.NewsList
import com.pratik.demoapp.db.AppDatabase
import com.pratik.home_listing_feature.data.model.toNewsList
import com.pratik.home_listing_feature.data.remote.ApiServices
import com.pratik.home_listing_feature.domain.repository.PostRepository

class PostRepositoryImpl(
    private val apiService: ApiServices,
    private val database: AppDatabase
) : PostRepository {

    override suspend fun getAllPost(category: String): List<NewsList> {
        val articles = apiService.getAllPost(category).articles
        
        val favoriteUrls = try {
            database.newsQueries.getAllFavorites().awaitAsList().map { it.url }.toSet()
        } catch (e: Exception) {
            emptySet()
        }

        return articles.map { article ->
            val news = article.toNewsList()
            news.copy(isFavorite = favoriteUrls.contains(news.url))
        }
    }

    override suspend fun saveNews(category: String, newsList: List<NewsList>) {
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

    override suspend fun getSavedNews(category: String): List<NewsList> {
        return database.newsQueries
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
    }

    override suspend fun updateFavorite(url: String, isFavorite: Boolean) {
        database.newsQueries.updateFavorite(isFavorite, url)
    }

    override suspend fun getAllFavorites(): List<NewsList> {
        return database.newsQueries
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
    }
}
