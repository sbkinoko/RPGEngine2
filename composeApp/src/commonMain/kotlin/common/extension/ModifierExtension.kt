package common.extension

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import core.menu.SelectableChildViewModel
import gamescreen.battle.command.CommandType
import gamescreen.battle.command.OnClick2
import values.Colors

@Composable
fun Modifier.menuItem(
    id: Int,
    childViewModel: SelectableChildViewModel<*>,
): Modifier {
    val selected by childViewModel
        .selectedFlowState
        .collectAsState()

    return selectable(
        isSelected = id == selected,
    ).clickable {
        childViewModel.onClickItem(
            id = id,
        )
    }
}

@Composable
fun <T : CommandType<T>> Modifier.menuItem2(
    id: T,
    onClick2: OnClick2<T>,
): Modifier {
    val selected by onClick2
        .selectedFlowState2
        .collectAsState()

    return selectable(
        isSelected = id == selected,
    ).clickable {
        onClick2.onClickItem(id = id)
    }
}

fun Modifier.selectable(
    isSelected: Boolean,
): Modifier {
    return border(
        width = 2.dp,
        shape = RectangleShape,
        color = if (isSelected) {
            Colors.SelectedMenu
        } else {
            Colors.MenuFrame
        },
    )
}

// fixme できることならModifier.OOでやりたい
val RowScope.equalAllocationModifier
    get() = Modifier
        .weight(1f)
        .fillMaxHeight()

val ColumnScope.equalAllocationModifier
    get() = Modifier
        .fillMaxWidth()
        .weight(1f)
