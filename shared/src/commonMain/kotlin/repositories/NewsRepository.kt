package repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import network.NewsAPI
import response.ArticleResponse

class NewsRepository {
    private val newsAPI = NewsAPI()
    private val _newsResponse = MutableStateFlow<ArticleResponse?>(null)
    val newsResponse: StateFlow<ArticleResponse?> = _newsResponse

    private val _searchResponse = MutableStateFlow<ArticleResponse?>(null)
    val searchResponse: StateFlow<ArticleResponse?> = _searchResponse


    suspend fun fetchTopHeadlines(category : String?) {
        val response = category?.let { newsAPI.getTopHeadlines(it) }
        _newsResponse.value = response

    }

    suspend fun fetchEverything(query: String) {
        val response = newsAPI.getEverything(query)
        _searchResponse.value = response

    }
}
