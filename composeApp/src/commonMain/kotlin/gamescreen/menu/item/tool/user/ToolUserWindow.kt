package gamescreen.menu.item.tool.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors
import common.values.Constants
import gamescreen.menu.item.list.UnSelectableItemList
import gamescreen.menu.item.list.UserList
import org.koin.compose.koinInject

@Composable
fun ToolUserWindow(
    modifier: Modifier = Modifier,
    toolUserViewModel: ToolUserViewModel = koinInject(),
) {
    val selectedId = toolUserViewModel.getSelectedAsState().value
    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            )
    ) {
        UserList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            itemUserViewModel = toolUserViewModel,
        )

        if (selectedId < Constants.playerNum) {
            UnSelectableItemList(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                selectedUserId = selectedId,
                itemList = toolUserViewModel,
            )
        } else {
            BagItemList(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                itemList = toolUserViewModel,
            )
        }
    }
}
