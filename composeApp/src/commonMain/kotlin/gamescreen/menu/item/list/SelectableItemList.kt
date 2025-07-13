package gamescreen.menu.item.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import common.extension.menuItem
import common.extension.pxToDp
import common.layout.CenterText
import common.layout.scrollInList
import core.menu.MenuItem
import kotlinx.coroutines.launch

private const val VISIBLE_ITEM_NUM = 10

@Composable
fun <T> SelectableItemList(
    selectedUserId: Int,
    menuItem: MenuItem<Int>,
    itemList: ItemList<T>,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        menuItem.collectFlow()
    }

    val scrollState = rememberScrollState()

    var height by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    // 対象が画面内に入るようにスクロール
    menuItem.scroll = {
        scope.launch {
            scrollInList(
                targetRow = it,
                height = height,
                itemNum = VISIBLE_ITEM_NUM,
                scrollState = scrollState,
            )
        }
    }

    Column(
        modifier = modifier.verticalScroll(
            state = scrollState,
        ).onGloballyPositioned {
            if (height == 0) {
                height = it.size.height
            }
        },
    ) {
        if (height == 0) {
            return@Column
        }

        itemList.getPlayerItemListAt(selectedUserId)
            .forEachIndexed { index, item ->
                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height / VISIBLE_ITEM_NUM).pxToDp())
                        .menuItem(
                            id = index,
                            menuItem = menuItem,
                        ),
                    text = item.name,
                )
            }
    }
}
