package gamescreen.battle.effect

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.delay
import values.Colors

private val rateList = listOf(
    0f,
    0.5f,
    0.6f,
    0.7f,
    0.8f,
    0.9f,
    1f,
    1f,
)

@Composable
fun AttackEffect() {
    var center by remember {
        mutableStateOf(0f)
    }

    var maxHeight by remember {
        mutableStateOf(0f)
    }

    var index by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(50)
            index++

            if (rateList.size <= index) {
                index = 0
            }
        }
    }

    val path = Path().apply {
        moveTo(center + 40f, 0f)
        lineTo(center - 40 + 80 * (1 - rateList[index]), maxHeight * rateList[index])
        lineTo(center + 80f, 40f)
        close()
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
            .onGloballyPositioned {
                center = it.size.width / 2f
                maxHeight = it.size.height.toFloat()
            }
    ) {
        drawPath(
            path = path,
            color = Colors.AttackFill,
            style = Fill,
        )

        drawPath(
            path = path,
            color = Colors.AttackBorder,
            style = Stroke(
                width = 2f,
            )
        )
    }
}
