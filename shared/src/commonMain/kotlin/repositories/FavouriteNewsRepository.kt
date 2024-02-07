package repositories

import dataclass.Article
import datasources.MockDataSourceFavourites

import kotlinx.coroutines.flow.MutableStateFlow
import response.ArticleResponse

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.update

class FavouriteNewsRepository {
    private val mockDataSourceFavourites = MockDataSourceFavourites()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _favouriteNewsResponse = MutableStateFlow<ArticleResponse?>(null)
    val favouriteNewsResponse = _favouriteNewsResponse

    companion object {
        private var instance: FavouriteNewsRepository? = null
        fun getInstance(): FavouriteNewsRepository {
            if (instance == null) {
                instance = FavouriteNewsRepository()
            }
            return instance!!
        }
    }

    init {
        updateFavouritesNews()
    }

    private fun updateFavouritesNews(){
        favouriteNewsResponse.update { getNewsFavourites()}
    }

    private fun getNewsFavourites() = mockDataSourceFavourites.getNewsFavourites()

    fun addFavourite(article: Article) {
        mockDataSourceFavourites.addFavourite(article)
    }

    fun removeFavourite(article: Article) {
        mockDataSourceFavourites.removeFavourite(article)
        updateFavouritesNews()
    }

    fun isFavourite(article: Article) = mockDataSourceFavourites.isFavourite(article)
}
