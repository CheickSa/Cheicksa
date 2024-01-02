package com.example.cheicksa.presentation.common_ui.restaurant

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cheicksa.ui.theme.montSarrat
import kotlin.math.abs
import kotlin.math.sin
import kotlin.random.Random

/**
 * A composable that displays a range slider along with a custom bar chart.
 *
 * @param startValue The starting value of the range slider.
 * @param endValue The ending value of the range slider.
 * @param steps The number of steps for the range slider.
 * @param onValueChangeFinished Callback triggered when the range slider value is changed and the user stops interaction.
 */
@Composable
fun RangeSliderExample(
    startValue: Float = 0f,
    endValue: Float = 100f,
    steps: Int = 100,
    onValueChangeFinished: (ClosedFloatingPointRange<Float>) -> Unit = {},

    ) {
    var sliderPosition by remember { mutableStateOf(startValue..endValue) }
    Column {
        // Put bars chart on top of the range slider using a negative offset
        Column (
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .rotate(180f)
                    .offset(y = (-25).dp),
            ) {
                for (i in 32 downTo 0) {
                    // Calculate the gap between bars
                    val gap = endValue / 32
                    // Calculate the height of the bar based on a sine function
                    val height = abs( 80 * sin(i.toFloat())).dp
                    Canvas(
                        modifier = Modifier
                            .width(8.dp)
                            .padding(start = 1.dp, end = 1.dp)
                            .height(height)

                    ) {
                        drawRect(
                            color = if (i * gap in sliderPosition) Color.Black
                            else Color.LightGray,
                            style = Fill,
                            size = Size(size.width, size.height)
                        )
                    }
                }
            }

            RangeSlider(
                value = sliderPosition,
                steps = steps,
                onValueChange = { range -> sliderPosition = range },
                valueRange = startValue..endValue,
                onValueChangeFinished = {
                    onValueChangeFinished.invoke(sliderPosition)
                },
                modifier = Modifier,
                colors = SliderDefaults.colors(
                    inactiveTickColor = Color.Transparent,
                    activeTickColor = Color.Transparent,
                )
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Column (
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ){
                    Text(
                        text = "Min",
                        fontFamily = montSarrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                    Text(
                        text = sliderPosition.start.toInt().toString(),
                        fontFamily = montSarrat,
                    )
                }
            }
            Divider(modifier = Modifier
                .weight(0.2f)
                .padding(horizontal = 2.dp)
                ,
                color = Color.Black,
                thickness = 2.dp
            )
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Column (
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ){
                    Text(
                        text = "Max",
                        fontFamily = montSarrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )
                    Text(
                        text = sliderPosition.endInclusive.toInt().toString(),
                        fontFamily = montSarrat,
                    )
                }
            }
        }

    }
}