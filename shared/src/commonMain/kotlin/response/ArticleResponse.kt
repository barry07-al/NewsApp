package response

import dataclass.Article

@kotlinx.serialization.Serializable
data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
