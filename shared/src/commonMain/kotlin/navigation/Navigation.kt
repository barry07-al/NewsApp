package navigation

import androidx.compose.runtime.*
import dataclass.Article
import dataclass.SourceData
import enumdata.NewsCategory
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import repositories.FavouriteNewsRepository
import repositories.NewsRepository
import ui.favourites.FavouritesResultsScreen
import ui.news.NewScreen
import ui.news.NewsScreen
import ui.search.SearchResultsScreen


val newsRepository = NewsRepository.getInstance()
val favouritesRepository = FavouriteNewsRepository.getInstance()

@Composable
fun Navigation() {
    val navigator = rememberNavigator()
    val selectedArticle = remember { mutableStateOf<Article?>(null) }
    val searchKeyword = remember { mutableStateOf<String?>(null) }
    val selectedCategory = remember { mutableStateOf(NewsCategory.GENERAL) }
    val selectedSource = remember { mutableStateOf<SourceData?>(null) }
    val selectedCountry = remember { mutableStateOf<String?>(null) }


    LaunchedEffect(selectedCategory.value) {
        newsRepository.fetchTopHeadlines(selectedCategory.value.name.lowercase())
    }


    LaunchedEffect(Unit) {
        newsRepository.fetchSources()
    }

    LaunchedEffect(selectedSource.value) {
        selectedSource.value?.let {
            newsRepository.fetchTopHeadlinesBySource(it.id)
        }
    }

    LaunchedEffect(selectedCountry.value) {
        selectedCountry.value?.let {
            newsRepository.fetchTopHeadlinesByCategoryAndCountry(
                selectedCategory.value.name.lowercase(),
                it
            )
        }
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
                newsRepository.sourceResponse.collectAsState().value?.let { it1 ->
                    NewsScreen(
                        navigator = navigator,
                        articleResponse = articleResponse,
                        sourceResponse = it1,
                        onSelectArticle = { article ->
                            selectedArticle.value = article
                            navigator.navigate("/newScreen")
                        },
                        searchKeyword = searchKeyword,
                        selectedCategory = selectedCategory,
                        favouriteNewsRepository = favouritesRepository,
                        selectedSource = selectedSource,
                        onCountrySelected = { country ->
                            selectedCountry.value = country
                        }
                    )
                }
            }
        }
        scene(route = "/newScreen") {
            selectedArticle.value?.let { article ->
                NewScreen(
                    article,
                    onBackClicked = {
                        navigator.popBackStack()
                    },
                    favouriteNewsRepository = favouritesRepository
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
                        },
                        favouriteNewsRepository = favouritesRepository
                    )
                }
            }
        }
        scene(route = "/favouriteNewsScreen") {
            FavouritesResultsScreen(
                articleResponse = favouritesRepository.favouriteNewsResponse.collectAsState().value!!,
                onBackClicked = {
                    navigator.popBackStack()
                },
                onSelectArticle = { article ->
                    selectedArticle.value = article
                    navigator.navigate("/newScreen")
                },
                favouriteNewsRepository = favouritesRepository
            )
        }
    }
}



