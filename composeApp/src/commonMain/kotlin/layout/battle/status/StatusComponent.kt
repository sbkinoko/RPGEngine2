package layout.battle.status

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatusComponent(
    modifier: Modifier = Modifier,
) {
    val hp = 10
    val maxHp = 100

    val mp = 5
    val maxMp = 50

    Column(modifier = modifier) {
        Text(
            text = "player"
        )
        Text(
            text = "HP $hp/$maxHp"
        )
        Text(
            text = "MP $mp/$maxMp"
        )
    }
}
