package gamescreen.battle.command.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import common.layout.DisableBox

@Composable
fun ItemCommandWindow(
    itemCommandViewModel: ItemCommandViewModel,
    modifier: Modifier = Modifier,
) {
    val flg = remember {
        mutableStateOf(true)
    }

    if (flg.value) {
        itemCommandViewModel.init()
        flg.value = false
    }

    Column(modifier = modifier) {
        for (i in 0 until 3) {
            Row(
                modifier = equalAllocationModifier,
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
    itemCommandViewModel: ItemCommandViewModel,
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
    itemCommandViewModel: ItemCommandViewModel,
    modifier: Modifier = Modifier,
) {
    val id = itemCommandViewModel.itemList[position]
    DisableBox(
        isDisable = itemCommandViewModel.canUse(id = id).not(),
        modifier = modifier
            .menuItem(
                id = position,
                childViewModel = itemCommandViewModel,
            )
    ) {
        CenterText(
            modifier = Modifier.fillMaxSize(),
            text = itemCommandViewModel.getName(id = id),
        )
    }
}
