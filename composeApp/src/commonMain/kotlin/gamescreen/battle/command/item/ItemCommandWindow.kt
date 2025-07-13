package gamescreen.battle.command.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.extension.pxToDp
import common.layout.CenterText
import common.layout.DisableBox
import common.layout.scrollInList
import kotlinx.coroutines.launch

private const val ItemNum = 3

@Composable
fun ItemCommandWindow(
    itemCommandViewModel: ItemCommandViewModel<*, *>,
    modifier: Modifier = Modifier,
) {
    val flg = remember {
        mutableStateOf(true)
    }

    if (flg.value) {
        itemCommandViewModel.init()
        flg.value = false
    }

    val scrollState = rememberScrollState()

    var height by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    // 対象が画面内に入るようにスクロール
    itemCommandViewModel.scroll = {
        scope.launch {
            scrollInList(
                targetRow = it / 2,
                height = height,
                itemNum = ItemNum,
                scrollState = scrollState,
            )
        }
    }

    Column(
        modifier = modifier.verticalScroll(
            scrollState,
        ).onGloballyPositioned {
            if (height == 0) {
                height = it.size.height
            }
        }
    ) {
        if (height == 0) {
            return@Column
        }

        for (i in 0 until (itemCommandViewModel.itemList.size + 1) / 2) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(
                        height = (height / ItemNum).pxToDp(),
                    ),
            ) {
                ItemArea(
                    itemCommandViewModel = itemCommandViewModel,
                    index = 2 * i,
                    size = itemCommandViewModel.itemList.size,
                    modifier = equalAllocationModifier,
                )

                ItemArea(
                    itemCommandViewModel = itemCommandViewModel,
                    index = 2 * i + 1,
                    size = itemCommandViewModel.itemList.size,
                    modifier = equalAllocationModifier,
                )
            }
        }
    }
}

@Composable
fun ItemArea(
    index: Int,
    size: Int,
    itemCommandViewModel: ItemCommandViewModel<*, *>,
    modifier: Modifier = Modifier,
) {
    if (index < size) {
        ItemText(
            itemCommandViewModel = itemCommandViewModel,
            modifier = modifier,
            position = index,
        )
    } else {
        Spacer(
            modifier = modifier,
        )
    }
}

@Composable
fun ItemText(
    position: Int,
    itemCommandViewModel: ItemCommandViewModel<*, *>,
    modifier: Modifier = Modifier,
) {
    DisableBox(
        isDisable = itemCommandViewModel.canUse(position = position).not(),
        modifier = modifier
            .menuItem(
                id = position,
                menuItem = itemCommandViewModel,
            )
    ) {
        CenterText(
            modifier = Modifier.fillMaxSize(),
            text = itemCommandViewModel.getName(position = position),
        )
    }
}
