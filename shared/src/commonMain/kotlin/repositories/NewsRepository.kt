package repositories

import kotlinx.coroutines.flow.MutableStateFlow
import network.NewsAPI
import response.ArticleResponse
import response.SourceResponse

class NewsRepository {
    private val newsAPI = NewsAPI()
    private val _newsResponse = MutableStateFlow<ArticleResponse?>(null)
    val newsResponse = _newsResponse

    private val _searchResponse = MutableStateFlow<ArticleResponse?>(null)
    val searchResponse =  _searchResponse

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

    suspend fun fetchSources(category: String) {
        val response = newsAPI.getSources(category)
        _sourceResponse.value = response
    }

    suspend fun fetchTopHeadlinesByCategoryAndSource(category: String, source: String) {
        val response = newsAPI.getTopHeadlinesByCategoryAndSource(category, source)
        _newsResponse.value = response
    }
}
