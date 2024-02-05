package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.primarySurface
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dataclass.Article

@Composable
fun NewScreen(article: Article, onBackClicked: () -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = article.source?.name ?: "Source unknown",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {onBackClicked()}
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
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
            NewBodyScreen(article)
        }
    }
}

@Composable
fun NewBodyScreen(article: Article) {
    val isArticleSaved = remember { mutableStateOf(false) }

    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ParallaxImage(url = article.urlToImage)

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "Published on ${article.publishedAt}",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Implémentez la logique de partage ici */ }) {
                        Icon(Icons.Default.Share, contentDescription = "shared article")
                    }
                    IconButton(onClick = { isArticleSaved.value = !isArticleSaved.value }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = if (isArticleSaved.value) "removed to saved" else "save article",
                        )
                    }
                }
                article.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                var content = article.content?.substringBefore("… [+")
                content = content.plus(article.url.let { " [read more on]($it)" })
                Text(
                    text = content,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
fun ParallaxImage(url: String?) {
    url?.let {
        Image(
            painter = rememberImagePainter(url),
            contentDescription = "Couverture de l'article",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}

/**
@Composable
fun ArticleLinkText(articleText: String, articleUrl: String) {
    val context = LocalContext.current

    val annotatedText = buildAnnotatedString {
        append(articleText)
        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 16.sp)) {
            append(" lire la suite")
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl)))
        }
    )
}
*/
