package gamescreen.menu.item.equipment.target

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.target.ItemTargetWindow
import org.koin.compose.koinInject

@Composable
fun EquipmentTargetWindow(
    modifier: Modifier = Modifier,
    targetViewModel: EquipmentTargetViewModel = koinInject(
    ),
) {
    ItemTargetWindow(
        modifier = modifier,
        itemTargetViewModel = targetViewModel,
    )
}
