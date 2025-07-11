package gamescreen.menu.item.abstract.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import core.domain.item.Item
import core.menu.MenuItem
import gamescreen.menu.item.list.SelectableItemList
import gamescreen.menu.item.list.UserList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import values.Colors

@Composable
fun <T, V : Item> UserWindow(
    itemUserViewModel: ItemUserViewModel<T, V>,
    modifier: Modifier = Modifier,
) {
    val selectedId by itemUserViewModel.selectedFlowState.collectAsState()

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
            itemUserViewModel = itemUserViewModel,
        )

        SelectableItemList(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            selectedUserId = selectedId,
            itemList = itemUserViewModel,
            menuItem = object : MenuItem<Int> {
                override fun onClick(id: Int) {
                }

                override val selectedFlowState: StateFlow<Int>
                    get() = MutableStateFlow(-1).asStateFlow()
            },
        )
    }
}
