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
        _newsResponse.value = category?.let { newsAPI.getTopHeadlines(it) }
    }

    suspend fun fetchEverything(query: String) {
        _searchResponse.value = newsAPI.getEverything(query)
    }

    suspend fun fetchSources() {
        _sourceResponse.value = newsAPI.getSources()
    }

    suspend fun fetchTopHeadlinesByCategoryAndCountry(category: String, countryCode: String) {
        _newsResponse.value = newsAPI.getTopHeadlinesByCategoryAndCountry(category, countryCode)
    }

    suspend fun fetchTopHeadlinesBySource(sourceId: String) {
        _newsResponse.value = newsAPI.getTopHeadlinesBySource(sourceId)
    }
}
