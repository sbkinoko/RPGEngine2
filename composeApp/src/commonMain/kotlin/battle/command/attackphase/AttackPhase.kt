package battle.command.attackphase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AttackPhase(
    playerName: String,
    targetName: String,
    attackPhaseCallBack: AttackPhaseCommandCallBack,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {
                attackPhaseCallBack.pressA.invoke()
            },
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = playerName + "の" + targetName + "への攻撃",
        )
    }
}
