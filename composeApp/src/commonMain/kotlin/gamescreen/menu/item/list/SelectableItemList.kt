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
            val targetTop = height / VISIBLE_ITEM_NUM * it
            val targetBottom = height / VISIBLE_ITEM_NUM * (it + 1)

            val top = scrollState.value
            val bottom = scrollState.value + height

            if (targetTop < top) {
                scrollState.animateScrollTo(targetTop)
            }

            if (bottom < targetBottom) {
                scrollState.animateScrollTo(targetTop - height / VISIBLE_ITEM_NUM * (VISIBLE_ITEM_NUM - 1))
            }
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
