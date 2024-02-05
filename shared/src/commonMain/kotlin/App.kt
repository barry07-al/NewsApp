import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import navigation.Navigation
// import screen.PreviewSearchResultsScreen


//private val newsRepository = NewsRepository()

@Composable
fun App() {
    //val articles by newsRepository.articlesState.collectAsState()

    MaterialTheme {
        PreComposeApp{ Navigation() }
        //PreviewSearchResultsScreen()
    }

}
