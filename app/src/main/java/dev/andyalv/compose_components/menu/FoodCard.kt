package dev.andyalv.compose_components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.andyalv.compose_components.navigation.AppScreens
import java.util.Locale
import java.util.UUID

@Composable
fun FoodCard(item: FoodItemGeneral, navController: NavController) {
    val displayPrice: Double = item.priceDiscount ?: item.price
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .clickable {
                navController.navigate(AppScreens.ItemScreen.route+"/${item.id}")
            }
    ){
        // Left side
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(14.dp)
        ){
            // Title
            Text(
                text=item.title,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            )

            PriceRow(
                displayPrice = displayPrice,
                price = item.price,
                priceDiscount = item.priceDiscount,
                likeability = item.likeability,
            )

            // Description
            Text(
                text = item.description,
                color = Color.Gray,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            // Discount tag
            if (item.priceDiscount != null) {
                DiscountTag(
                    price = item.price,
                    priceDiscount = item.priceDiscount
                )
            }
        }

        // Right side
        ImageBox(
            imageUrl = item.image.url,
            imageDescription = item.image.description,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PriceRow(
    priceDiscount: Double?,
    likeability: FoodLikeability?,
    displayPrice: Double,
    price: Double
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        // Display price
        TextPrice(
            text = String.format(
                Locale.forLanguageTag("es-MX"),
                "$%.2f",
                displayPrice
            ),
        )

        // Discount strikethrough
        if (priceDiscount != null) {
            TextPrice(
                text = String.format(
                    Locale.forLanguageTag("es-MX"),
                    "$%.2f",
                    price
                ),
                isStrikeThrough = true
            )
        }

        // Likeability
        if (likeability != null) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(Color.Black, CircleShape)
            )
            // Thumbs up icon
            Icon(
                imageVector = Icons.Outlined.ThumbUp,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
            )

            // Like percentage - d%
            TextPrice(
                text = String.format(
                    Locale.forLanguageTag("es-MX"),
                    "%d%%",
                    likeability.likePercentage
                )
            )

            // Like users - (d)
            TextPrice(
                text = String.format(
                    Locale.forLanguageTag("es-MX"),
                    "(%d)",
                    likeability.likeUsers
                )
            )
        }
    }
}

@Composable
fun DiscountTag(
    priceDiscount: Double,
    price: Double,
){
    val discountPercentage: Double = 100 - ((priceDiscount * 100) / price)

    Text(
        text = String.format(
            Locale.forLanguageTag("es-MX"),
            "− %.0f%%",
            discountPercentage
        ),
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .background(
                color = Color.Red,
                shape = RoundedCornerShape(2.dp)
            )
            .padding(1.dp)
    )
}

@Composable
fun ImageBox(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageDescription: String?
){
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = imageDescription,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 10.dp,
                        bottomEnd = 10.dp,
                        bottomStart = 0.dp
                    )
                ),
            contentScale = ContentScale.Crop
        )
        SmallFloatingActionButton(
            onClick = {},
            containerColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .size(30.dp)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
            )
        }
    }
}

@Composable
fun TextPrice(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    text: String,
    isStrikeThrough: Boolean = false
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Light,
        modifier = modifier,
        textDecoration = if (isStrikeThrough) TextDecoration.LineThrough else null,
        color = if (isStrikeThrough) Color.Gray else Color.Black
    )
}

@Preview
@Composable
fun PreviewFun(){
    FoodCard(
        FoodItemGeneral(
            id = UUID.fromString("8e2d7e2b-8a7b-4ebe-9b73-cab1f8d534c4"),
            title = "Title",
            description = "Lorem ipsum dolor sit amet consectetur adipiscing, elit erat habitant ultricies.",
            price = 300.0,
            priceDiscount = 150.0,
            likeability = FoodLikeability(
                likePercentage = 98,
                likeUsers = 29
            ),
            image = FoodItemImageData(
                url = "https://assets.tmecosys.com/image/upload/t_web_rdp_recipe_584x480_1_5x/img/recipe/ras/Assets/2F60018A-7D11-48CA-BB83-B32497E02BF5/Derivates/bc77ea56-073d-4648-82a4-0782bbf1d37c.jpg",
            )
        ),
        navController = rememberNavController()
    )
}