package battle.monster

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import battle.domain.SelectedEnemyState

@Composable
fun Arrow(
    index: Int,
    selectedEnemyState: SelectedEnemyState,
    modifier: Modifier = Modifier,
) {
    if (
        selectedEnemyState.selectedEnemy.contains(index)
    ) {
        Text(
            modifier = modifier,
            text = "↓",
            textAlign = TextAlign.Center,
        )
    } else {
        Spacer(
            modifier = modifier,
        )
    }
}
