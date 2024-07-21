package menu.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.layout.CenterText
import common.values.Colors
import menu.domain.MainMenuItem


@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    mainMenuItemList: List<MainMenuItem> = listOf(),
) {
    val pairedList: MutableList<Pair<MainMenuItem, MainMenuItem?>> = mutableListOf()
    for (cnt: Int in mainMenuItemList.indices step 2) {
        if (cnt + 1 < mainMenuItemList.size) {
            //　次の項目とセットで追加
            pairedList.add(Pair(mainMenuItemList[cnt], mainMenuItemList[cnt + 1]))
        } else {
            // もう項目がないのでnullを入れる
            pairedList.add(Pair(mainMenuItemList[cnt], null))
        }
    }

    Column(
        modifier = modifier.background(
            Colors.MenuAra
        ),
    ) {
        pairedList.forEach {
            Row(
                modifier = equalAllocationModifier,
            ) {
                CenterText(
                    modifier = equalAllocationModifier,
                    text = it.first.text,
                )

                it.second?.let {
                    CenterText(
                        modifier = equalAllocationModifier,
                        text = it.text,
                    )
                } ?: run {
                    Spacer(
                        modifier = equalAllocationModifier,
                    )
                }
            }
        }

    }
}

val RowScope.equalAllocationModifier
    get() = Modifier
        .weight(1f)
        .fillMaxHeight()

val ColumnScope.equalAllocationModifier
    get() = Modifier
        .fillMaxHeight()
        .weight(1f)
