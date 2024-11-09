package root.customapplication.mainactivity.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import java.util.Locale
import stoyicker.interviewdemo.app.R

@Composable
internal fun CharacterEntryCard(name: String?, image: String?, size: Dp) {
  val shape = MaterialTheme.shapes.small
  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .size(size)
      .padding(4.dp)
      .clip(shape)
      .clickable {},
    shape = shape
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .clip(shape)
    ) {
      image?.let {
        AsyncImage(
          placeholder = painterResource(R.drawable.placeholder_list_entry_image),
          modifier = Modifier
            .fillMaxWidth()
            .clip(shape),
          contentDescription = name,
          contentScale = ContentScale.Crop,
          model = it
        )
      }
      name?.let {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
          verticalArrangement = Arrangement.Bottom
        ) {
          Text(
            text = it.uppercase(Locale.ENGLISH),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
              shadow = Shadow(
                color = MaterialTheme.colorScheme.inversePrimary,
                offset = Offset(2F, 2F),
                blurRadius = 4F
              )
            )
          )
        }
      }
    }
  }
}
