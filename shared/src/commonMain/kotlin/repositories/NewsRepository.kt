package repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import network.NewsAPI
import response.ArticleResponse
import response.SourceResponse

class NewsRepository {
    private val newsAPI = NewsAPI()
    private val _newsResponse = MutableStateFlow<ArticleResponse?>(null)
    val newsResponse: StateFlow<ArticleResponse?> = _newsResponse

    private val _searchResponse = MutableStateFlow<ArticleResponse?>(null)
    val searchResponse: StateFlow<ArticleResponse?> = _searchResponse

    private val _sourceResponse = MutableStateFlow<SourceResponse?>(null)
    val sourceResponse = _sourceResponse

    companion object {
        private var instance: NewsRepository? = null
        fun getInstance(): NewsRepository {
            if (instance == null) {
                instance = NewsRepository()
            }
            return instance!!
        }
    }

    suspend fun fetchTopHeadlines(category : String?) {
        val response = category?.let { newsAPI.getTopHeadlines(it) }
        _newsResponse.value = response
    }

    suspend fun fetchEverything(query: String) {
        val response = newsAPI.getEverything(query)
        _searchResponse.value = response
    }

    suspend fun fetchSources() {
        val response = newsAPI.getSources()
        _sourceResponse.value = response
    }
}
