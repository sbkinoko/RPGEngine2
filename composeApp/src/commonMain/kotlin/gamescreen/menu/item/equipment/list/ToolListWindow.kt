package gamescreen.menu.item.equipment.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.itemselect.ItemListWindow
import org.koin.compose.koinInject

@Composable
fun EquipmentListWindow(
    modifier: Modifier = Modifier,
    listViewModel: EquipmentListViewModel = koinInject(),
) {
    ItemListWindow(
        modifier = modifier,
        itemListViewModel = listViewModel,
    )
}
