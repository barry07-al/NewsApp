package screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun FavouriteView(navigator: Navigator) {
        IconButton(onClick = {
            navigator.navigate("/favouriteNewsScreen")
        }) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "Saved Articles",
                tint = Color.White
            )
        }
}