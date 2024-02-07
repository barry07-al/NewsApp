package screen

import enumdata.NewsCategory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.primarySurface
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import dataclass.SourceData
import moe.tlaster.precompose.navigation.Navigator
import response.SourceResponse


@Composable
fun AppHeader(
    navigator: Navigator,
    searchKeyword: MutableState<String?>,
    selectedCategory: MutableState<NewsCategory>,
    sourceSelected: MutableState<SourceData?>,
    sourceResponse: SourceResponse,
) {
    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    val showSearch = remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (showSearch.value) {
                        SearchView(
                            state = searchState,
                            onSearchSubmit = { keyword ->
                                searchKeyword.value = keyword
                                navigator.navigate("/searchResultsScreen")
                                showSearch.value = false
                                searchState.value = TextFieldValue("")
                            }
                        )
                    } else {
                        Text("NewsApp", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            },
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface),
            actions = {
                if (!showSearch.value) {
                    FavouriteView(navigator)
                    IconButton(onClick = { showSearch.value = true }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    MainDropdown(
                        onCategorySelected = { category -> selectedCategory.value = category },
                        sourceResponse = sourceResponse,
                        onSourceSelected = { source -> sourceSelected.value = source }
                    )
                } else {
                    IconButton(onClick = { showSearch.value = false }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
            }
        )
    }
}
