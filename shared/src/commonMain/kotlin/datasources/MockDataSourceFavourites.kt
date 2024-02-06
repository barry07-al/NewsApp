package datasources

import dataclass.Article
import response.ArticleResponse

class MockDataSourceFavourites {
    private val favourites = mutableListOf<Article>()

    fun getNewsFavourites(): ArticleResponse {
        return ArticleResponse("ok", favourites.size, favourites)
    }

    fun addFavourite(article: Article) {
        favourites.add(article)
    }

    fun removeFavourite(article: Article) {
        favourites.remove(article)
    }

    fun isFavourite(article: Article) = favourites.contains(article)
}