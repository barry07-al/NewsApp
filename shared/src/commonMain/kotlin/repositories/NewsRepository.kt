package repositories

import kotlinx.coroutines.flow.MutableStateFlow
import datasources.network.NewsAPI
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

    suspend fun fetchSources() {
        val response = newsAPI.getSources()
        _sourceResponse.value = response
    }

    suspend fun fetchTopHeadlinesByCategoryAndCountry(category: String, countryCode: String) {
        val response = newsAPI.getTopHeadlinesByCategoryAndCountry(category, countryCode)
        _newsResponse.value = response
    }

    suspend fun fetchTopHeadlinesBySource(sourceId: String) {
        val response = newsAPI.getTopHeadlinesBySource(sourceId)
        _newsResponse.value = response
    }
}
