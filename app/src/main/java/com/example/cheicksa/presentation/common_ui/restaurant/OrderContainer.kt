package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cheicksa.R
import com.example.cheicksa.model.restaurant.Comments
import com.example.cheicksa.presentation.viewmodels.MenuViewModel
import com.example.cheicksa.ui.theme.montSarrat
import org.w3c.dom.Comment

/**
 * [OrderContainer] is a composable function that represents a container card for displaying information about a food item in an order.
 * It includes the food name, ingredients, price, and an image. The card is clickable, and clicking on it invokes the provided [onClick] lambda.
 *
 * @param foodName The name of the food item.
 * @param ingredient The ingredients or description of the food item.
 * @param price The price of the food item.
 * @param painter The resource ID of the image representing the food item.
 * @param onClick Lambda function invoked when the order container is clicked.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderContainer(
    foodName: String,
    ingredient: String,
    price: Double,
    image: String? = null,
    likes: Int = 10,
    dislikes: Int = 10,
    comments: List<Comments> = listOf(
        Comments(
            name = "Cheick",
            comment = "This is a comment",
            date = "12/12/2021"
        ),
        Comments(),
    ),
    showcomments: Boolean = false,
    onClick: () -> Unit,

) {
    var showComments by remember { mutableStateOf(false) }
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
        ) {
            // Card composable for the order container
            Card(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                // Row composable for arranging content horizontally
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = foodName,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier,
                            fontFamily = montSarrat

                        )
                        Text(
                            text = ingredient,
                            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.width(200.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            lineHeight = 1.2.em,
                            fontFamily = montSarrat

                        )
                        Text(
                            text = price.toString() + " " + stringResource(id = R.string.devise) + " ",
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier,
                            fontFamily = montSarrat
                        )
                    }
                    if (image == null || !(image as String).startsWith("http")) {
                        Button(
                            onClick = onClick,
                            elevation =  ButtonDefaults.buttonElevation(
                                5.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            ),
                            shape = CircleShape,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 15.dp, bottom = 0.dp, end = 15.dp)
                                .align(Alignment.CenterVertically),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription ="",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        AsyncImage(
                            model = image,
                            contentDescription = "",
                            modifier = Modifier
                                .width(75.dp)
                                .fillMaxHeight()
                                .padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                    }
                }
            }
            if (image != null && (image as String).startsWith("http")) {
                Button(
                    onClick = onClick,
                    elevation = ButtonDefaults.buttonElevation(
                        5.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    shape = CircleShape,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp, bottom = 0.dp, end = 10.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = (-25).dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = showcomments,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutLinearInEasing
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutLinearInEasing
                )
            )
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .offset(y = (-5).dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.thumbs_up_solid),
                        contentDescription = ""
                    )
                    Text(text = likes.toString(), fontSize = 2.5.em)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.thumbs_up_solid),
                        contentDescription = "",
                        modifier = Modifier
                            .rotate(180f)
                    )
                    Text(text = dislikes.toString(), fontSize = 2.5.em)
                }
                Icon(
                    painter = painterResource(id = R.drawable.comment_regular),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            showComments = true
                        }
                )
            }
        }
    }
    if (showComments&& showcomments) {
        ModalBottomSheet(
            onDismissRequest = { showComments = false },
            containerColor = MaterialTheme.colorScheme.tertiary,
        ) {
            CommentContainer(comments)
        }
    }

}

@Composable
fun CommentContainer(
    comments: List<Comments> = emptyList(),
) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .height(700.dp)
    ) {
        LazyColumn {
            items(comments.size){
                val comment = comments[it]
                CommentField(comment = comment,)
            }
        }
    }
}

@Composable
fun CommentField(
    comment: Comments = Comments(),
) {
    var liked by rememberSaveable { mutableStateOf(false) }
    var disliked by rememberSaveable { mutableStateOf(false) }

    Row  (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ){
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.onSurface)
            ) {
                Text(
                    text = comment.name.first().toString(),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
            Column(
                modifier = Modifier
                    .padding(end = 45.dp)
            ) {
                Text(
                    text = comment.name,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = comment.comment,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,

                    )
                Text(
                    text = comment.date,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        Row (
            modifier = Modifier
                .padding(end = 15.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                liked = !liked
                disliked = false
            }) {
                Icon(
                    painter = if (liked) painterResource(id = R.drawable.thumbs_up_solid)
                    else painterResource(id = R.drawable.thumbs_up_regular),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = {
                disliked = !disliked
                liked = false
            }) {
                Icon(
                    painter = if (disliked) painterResource(id = R.drawable.thumbs_up_solid)
                    else painterResource(id = R.drawable.thumbs_up_regular),
                    contentDescription = "",
                    modifier = Modifier.rotate(180f),
                    tint = Color.Unspecified
                )
            }
        }

    }
}