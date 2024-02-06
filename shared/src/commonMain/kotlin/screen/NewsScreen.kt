package screen

import dataclass.Article
import response.ArticleResponse

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import enumdata.NewsCategory
import moe.tlaster.precompose.navigation.Navigator
import repositories.FavouriteNewsRepository

@Composable
fun NewsScreen(
    navigator: Navigator,
    articleResponse: ArticleResponse,
    onSelectArticle: (Article) -> Unit,
    searchKeyword: MutableState<String?>,
    selectedCategory: MutableState<NewsCategory>,
    favouriteNewsRepository: FavouriteNewsRepository
) {
    Scaffold(
        topBar = {
            AppHeader(navigator, searchKeyword, selectedCategory)
        },
        bottomBar = {
            AppFooter()
        }
    ) {
        innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TitleComposable(title = "Top Headlines: ${selectedCategory.value.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}")
                BodyContent(articleResponse, onSelectArticle, favouriteNewsRepository)
            }
    }
}

