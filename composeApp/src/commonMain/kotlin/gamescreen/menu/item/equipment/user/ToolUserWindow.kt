package gamescreen.menu.item.equipment.user

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gamescreen.menu.item.abstract.user.UserWindow
import org.koin.compose.koinInject

@Composable
fun EquipmentUserWindow(
    modifier: Modifier = Modifier,
    userViewModel: EquipmentUserViewModel = koinInject(),
) {
    UserWindow(
        modifier = modifier,
        itemUserViewModel = userViewModel,
    )
}
