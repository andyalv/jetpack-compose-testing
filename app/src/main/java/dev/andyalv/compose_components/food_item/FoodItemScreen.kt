package dev.andyalv.compose_components.food_item

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.andyalv.compose_components.App
import dev.andyalv.compose_components.menu.FoodItemGeneral
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemDetailScreen(
    itemId: UUID
){
    val app = LocalContext.current.applicationContext as App
    val repo = app.repository

    val item = repo.getFoodItem(itemId)
    val detail = repo.getFoodDetail(itemId)

    Scaffold(
        topBar = {
            TopAppBar(
                title =
                    {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        MainBody(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            item = item,
            detail = detail
        )
    }
}

@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    item: FoodItemGeneral,
    detail: FoodItemDetail,
){
    var isLoading by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {

        AsyncImage(
            model = item.image.url,
            contentDescription = item.image.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(100.dp)
                .alpha(0.8f)
        )

        if (isLoading)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        Column (
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(item.description, style = MaterialTheme.typography.bodyLarge, color = Color.Gray, fontStyle = FontStyle.Italic)

            OptionsSection(detail.optionList)
            ExtraSection(detail.extraList)

            Spacer(modifier = Modifier.height(80.dp))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp
                )
                .align(Alignment.BottomCenter),
            onClick = {
                isLoading = true
            }
        ) {
            Text("Guardar")
        }
    }
}

@Composable
fun OptionsSection(
    optionList: List<OptionIndividual>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        optionList.forEach { question ->
            Text(
                text = question.label,
                style = MaterialTheme.typography.titleMedium,
            )

            var selectedOption by remember { mutableStateOf(question.choices.first()) }

            question.choices.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(option)
                }
            }
        }
    }
}

@Composable
fun ExtraSection(
    extraList: List<ExtraIndividual>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text("Extras", style = MaterialTheme.typography.titleMedium)
        extraList.forEach { extraIndividual ->
            var state by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state,
                    onCheckedChange = { newState -> state = newState }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(extraIndividual.label)
                    TextPrice(price = extraIndividual.price, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun TextPrice(
    price: Double,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = String.format(
            "$%.2f",
            price
        ),
        color = color,
        style = style,
    )
}