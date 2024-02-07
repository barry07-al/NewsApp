package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import repositories.FavouriteNewsRepository
import response.ArticleResponse

val countryNamesToCodes = mapOf(
    "united arab emirates" to "ae",
    "argentina" to "ar",
    "austria" to "at",
    "australia" to "au",
    "belgium" to "be",
    "bulgaria" to "bg",
    "brazil" to "br",
    "canada" to "ca",
    "switzerland" to "ch",
    "china" to "cn",
    "colombia" to "co",
    "czech republic" to "cz",
    "germany" to "de",
    "egypt" to "eg",
    "france" to "fr",
    "united kingdom" to "gb",
    "greece" to "gr",
    "hong kong" to "hk",
    "hungary" to "hu",
    "indonesia" to "id",
    "ireland" to "ie",
    "israel" to "il",
    "india" to "in",
    "italy" to "it",
    "japan" to "jp",
    "south korea" to "kr",
    "lithuania" to "lt",
    "latvia" to "lv",
    "morocco" to "ma",
    "mexico" to "mx",
    "malaysia" to "my",
    "nigeria" to "ng",
    "netherlands" to "nl",
    "norway" to "no",
    "new zealand" to "nz",
    "philippines" to "ph",
    "poland" to "pl",
    "portugal" to "pt",
    "romania" to "ro",
    "serbia" to "rs",
    "russia" to "ru",
    "saudi arabia" to "sa",
    "sweden" to "se",
    "singapore" to "sg",
    "slovenia" to "si",
    "slovakia" to "sk",
    "thailand" to "th",
    "turkey" to "tr",
    "taiwan" to "tw",
    "ukraine" to "ua",
    "united states" to "us",
    "venezuela" to "ve",
    "south africa" to "za"
)


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
        val filteredArticles = articles.filter { it.title != "[Removed]"}  // Filter out articles removed
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
fun FilterByCountry(
    onCountrySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCountryCode by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.wrapContentSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "filter by country",
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
            countryNamesToCodes.forEach { (countryName, countryCode) ->
                DropdownMenuItem(onClick = {
                    selectedCountryCode = countryCode
                    onCountrySelected(countryCode)
                    expanded = false
                }) {
                    Text(
                        text = countryName.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
    }
}
