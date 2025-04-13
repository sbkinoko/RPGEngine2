package gamescreen.battle.effect

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import gamescreen.battle.repository.attackeffect.AttackEffectInfo
import values.Colors

@Composable
fun AttackEffect(
    attackEffectInfo: AttackEffectInfo? = null,
) {
    //エフェクトが見えなければ終了
    if (attackEffectInfo?.isVisible != true) {
        return
    }

    var center by remember {
        mutableStateOf(0f)
    }

    var maxHeight by remember {
        mutableStateOf(0f)
    }

    val path = attackEffectInfo.getPath(
        center = center,
        maxHeight = maxHeight,
    )

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
