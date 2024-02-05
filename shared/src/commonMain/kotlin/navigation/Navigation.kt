package navigation

import androidx.compose.runtime.*
import dataclass.Article
import enumdata.NewsCategory
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import repositories.NewsRepository
import screen.*


val newsRepository = NewsRepository()

@Composable
fun Navigation() {
    val navigator = rememberNavigator()
    val selectedArticle = remember { mutableStateOf<Article?>(null) }
    val searchKeyword = remember { mutableStateOf<String?>(null) }
    val selectedCategory = remember { mutableStateOf(NewsCategory.GENERAL) }

    LaunchedEffect(selectedCategory.value) {
        newsRepository.fetchTopHeadlines(selectedCategory.value.name.lowercase())
    }

    LaunchedEffect(searchKeyword.value) {
        searchKeyword.value?.let {
            newsRepository.fetchEverything(it)
        }
    }

    NavHost(navigator = navigator, initialRoute = "/newsScreen") {
        scene(route = "/newsScreen") {
            val articleResponse = newsRepository.newsResponse.collectAsState().value
            if (articleResponse != null) {
                NewsScreen(
                    navigator = navigator,
                    articleResponse = articleResponse,
                    onSelectArticle = { article ->
                        selectedArticle.value = article
                        navigator.navigate("/newScreen")
                    },
                    searchKeyword = searchKeyword,
                    selectedCategory = selectedCategory
                )
            }
        }
        scene(route = "/newScreen") {
            selectedArticle.value?.let { article ->
                NewScreen(
                    article,
                    onBackClicked = {
                        navigator.popBackStack()
                    }
                )
            }
        }
        scene(route = "/searchResultsScreen") {
            val searchResults = newsRepository.searchResponse.collectAsState().value
            searchKeyword.value?.let { keyword ->
                if (searchResults != null) {
                    SearchResultsScreen(
                        articleResponse = searchResults,
                        keyword = keyword,
                        onBackClicked = {
                            navigator.popBackStack()
                        },
                        onSelectArticle = { article ->
                            selectedArticle.value = article
                            navigator.navigate("/newScreen")
                        }
                    )
                }
            }
        }
        scene(route = "/favouriteNewsScreen") {
            FavouritesResultsScreen(
                articleResponse = fakeData(),
                onBackClicked = {
                    navigator.popBackStack()
                },
                onSelectArticle = { article ->
                    selectedArticle.value = article
                    navigator.navigate("/newScreen")
                }
            )
        }
    }
}



