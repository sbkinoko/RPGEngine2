package battle.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import battle.viewmodel.BattleViewModel

@Composable
fun CommandArea(
    modifier: Modifier = Modifier,
    battleViewModel: BattleViewModel,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        battleViewModel.attack(
                            target = 0,
                            damage = 10,
                        )
                    },
                text = "攻撃",
                // todo 文字サイズの自動調整を実装
                // https://zenn.dev/warahiko/articles/971f1ff0591f62
            )
            Spacer(
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

        }
    }
}
