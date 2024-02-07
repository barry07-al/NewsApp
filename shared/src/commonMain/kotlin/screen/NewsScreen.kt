package screen

import dataclass.Article
import response.ArticleResponse

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import dataclass.SourceData
import enumdata.NewsCategory
import moe.tlaster.precompose.navigation.Navigator
import repositories.FavouriteNewsRepository
import response.SourceResponse

@Composable
fun NewsScreen(
    navigator: Navigator,
    articleResponse: ArticleResponse,
    sourceResponse: SourceResponse,
    onSelectArticle: (Article) -> Unit,
    onsourceSelected: (SourceData) -> Unit,
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleComposable(title = selectedCategory.value.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                    FilterBySource(sourceResponse, onsourceSelected)
                }
                BodyContent(articleResponse, onSelectArticle, favouriteNewsRepository)
            }
    }
}

