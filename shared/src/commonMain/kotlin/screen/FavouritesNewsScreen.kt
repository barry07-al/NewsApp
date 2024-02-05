package screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dataclass.Article
import response.ArticleResponse

@Composable
fun FavouritesResultsScreen(articleResponse: ArticleResponse, onBackClicked: () -> Unit, onSelectArticle: (Article) -> Unit) {
    // val articles = articleResponse.articles
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favourites news",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {onBackClicked()}
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                backgroundColor = MaterialTheme.colors.primarySurface,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
            )
        },
        bottomBar = {
            AppFooter()
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BodyContent(articleResponse, onSelectArticle)
        }
    }
}