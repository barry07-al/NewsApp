package ui.news

import dataclass.Article
import response.ArticleResponse

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dataclass.SourceData
import enumdata.NewsCategory
import moe.tlaster.precompose.navigation.Navigator
import repositories.FavouriteNewsRepository
import response.SourceResponse
import ui.util.AppHeader
import ui.util.BodyContent
import ui.util.FilterByCountry
import ui.util.TitleComposable

@Composable
fun NewsScreen(
    navigator: Navigator,
    articleResponse: ArticleResponse,
    sourceResponse: SourceResponse,
    onSelectArticle: (Article) -> Unit,
    searchKeyword: MutableState<String?>,
    selectedCategory: MutableState<NewsCategory>,
    selectedSource: MutableState<SourceData?>,
    favouriteNewsRepository: FavouriteNewsRepository,
    onCountrySelected: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppHeader(
                navigator, searchKeyword, selectedCategory, selectedSource, sourceResponse)
        },
        bottomBar = {
            // AppFooter()
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
                    if (selectedSource.value == null) {
                        TitleComposable(title = selectedCategory.value.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                        FilterByCountry(onCountrySelected)
                    } else {
                        TitleComposable(title = ("Source: " + selectedSource.value?.name) ?: "Source unknown")
                    }
                }
                BodyContent(articleResponse, onSelectArticle, favouriteNewsRepository)
            }
    }
}

