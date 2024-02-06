package screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.sp
import dataclass.Source
import dataclass.SourceData
import enumdata.NewsCategory
import response.SourceResponse

@Composable
fun DropdownCategories(onCategorySelected: (NewsCategory) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val categories = NewsCategory.values()
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu des catégories",
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categories.forEach { category ->
                DropdownMenuItem(onClick = {
                    onCategorySelected(category)
                    expanded = false
                }) {
                    Text(
                        text = category.name,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun DropDownSources(
    sourceResponse: SourceResponse,
    category: NewsCategory,
    onSourceSelected: (SourceData) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "Menu des sources",
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            sourceResponse.sources.forEach { source ->
                DropdownMenuItem(
                    onClick = {
                        onSourceSelected(source)
                        expanded = false
                    }
                ) {
                    Text(
                        text = source.name,
                        fontSize = 12.sp,
                    )
                }
            }
            DropdownMenuItem(
                onClick = {
                    onSourceSelected(
                        SourceData(
                            "all",
                            "All",
                            "All",
                            "All",
                            "All",
                            "us",
                            "us"
                        )
                    )
                    expanded = false
                }
            ) {
                Text(
                    text = category.name,
                    fontSize = 10.sp,
                )
            }
        }
    }
}

