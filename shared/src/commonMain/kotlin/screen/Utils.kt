package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import dataclass.Article
import dataclass.SourceData
import repositories.FavouriteNewsRepository
import repositories.NewsRepository
import response.ArticleResponse
import response.SourceResponse

@Composable
fun BodyContent(
    articleResponse: ArticleResponse,
    onSelectArticle: (Article) -> Unit,
    favouriteNewsRepository: FavouriteNewsRepository
) {
    val articles = articleResponse.articles
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        val filteredArticles = articles.filter { it.title != "[Removed]"}  // Filter out articles with no image
        LazyColumn {
            items(filteredArticles) { article ->
                ArticleCard(article, onSelectArticle, favouriteNewsRepository)
            }
        }
    }
}

@Composable
fun TitleComposable(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun ArticleCard(
    article: Article,
    onSelectArticle: (Article) -> Unit,
    favouriteNewsRepository: FavouriteNewsRepository
) {
    var isFavourite by remember { mutableStateOf(favouriteNewsRepository.isFavourite(article)) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onSelectArticle(article) },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            article.urlToImage?.let { imageUrl ->
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = "Image de l'article",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            isFavourite = if (isFavourite) {
                                favouriteNewsRepository.removeFavourite(article)
                                false
                            } else {
                                favouriteNewsRepository.addFavourite(article)
                                true
                            }
                        }

                    ) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Ajouter aux favoris",
                            tint = if (isFavourite) Color.Red else Color.Black
                        )
                    }
                }
                Text(
                    text = article.description ?: "",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.75f)
                )
            }
        }
    }
}
@Composable
fun FilterBySource(
    sourceResponse: SourceResponse,
    onsourceSelected: (SourceData) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSource by remember { mutableStateOf<SourceData?>(null) }

    Box(modifier = Modifier.wrapContentSize()) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "filter by source",
                style = MaterialTheme.typography.body2,
            )
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "Filter"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            var isSelected by remember { mutableStateOf(false) }
            sourceResponse.sources.take(7).forEach { source ->
                DropdownMenuItem(onClick = {
                    selectedSource  = source
                    onsourceSelected(source)
                    expanded = false
                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = source.name,
                            style = MaterialTheme.typography.body2,
                        )
                        Checkbox(
                            checked = selectedSource == source,
                            onCheckedChange = {checked ->
                                if (checked) {
                                    selectedSource = source
                                    expanded = false
                                } else {
                                    selectedSource = null
                                }
                                onsourceSelected(source)
                            }
                        )
                    }
                }
            }
        }
    }
}
