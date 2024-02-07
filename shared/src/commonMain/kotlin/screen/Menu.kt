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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dataclass.SourceData
import enumdata.NewsCategory
import response.SourceResponse

@Composable
fun MainDropdown(
    onCategorySelected: (NewsCategory) -> Unit,
    sourceResponse: SourceResponse,
    onSourceSelected: (SourceData) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showCategories by remember { mutableStateOf(false) }
    var showSources by remember { mutableStateOf(false) }
    val categories = NewsCategory.values()
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "menu",
                tint = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                showCategories = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            DropdownMenuItem(
                onClick = {
                    showCategories = !showCategories
                    if (showCategories) {
                        showSources = false
                    }
                }
            ) {
                Text(
                    text = "Categories",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (showCategories) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        onCategorySelected(category)
                        expanded = false
                        showCategories = false
                    }) {
                        Text(
                            text = category.name.replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp,
                        )
                    }
                }
            }
            DropdownMenuItem(
                onClick = {
                    showSources = !showSources
                    if (showSources) {
                        showCategories = false
                    }
                }
            ) {
                Text(
                    text = "Sources",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (showSources) {
                sourceResponse.sources.forEach { source ->
                    DropdownMenuItem(onClick = {
                        onSourceSelected(source)
                        expanded = false
                        showSources = false
                    }) {
                        Text(
                            text = source.name,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}






