package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import response.ArticleResponse

class NewsAPI {

    private val url = "https://newsapi.org/v2"
    private val key = "b818431d7898460d9fd432b28b05a9d8"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
        }
    }

    suspend fun getTopHeadlines(category: String): ArticleResponse =
        httpClient.get("$url/top-headlines?category=$category&country=us&apiKey=$key").body()

    suspend fun getEverything(query: String): ArticleResponse =
        httpClient.get("$url/everything?q=$query&apiKey=$key").body()

}