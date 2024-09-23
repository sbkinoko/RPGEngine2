package common.extension

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.values.Colors
import core.menu.SelectableChildViewModel

@Composable
fun Modifier.menuItem(
    id: Int,
    childViewModel: SelectableChildViewModel<*>,
): Modifier {
    return selectable(
        id = id,
        selected = childViewModel.getSelectedAsState().value,
    ).clickable {
        childViewModel.onClickItem(
            id = id,
        )
    }
}

fun Modifier.selectable(
    id: Int,
    selected: Int,
): Modifier {
    return border(
        width = 2.dp,
        shape = RectangleShape,
        color = if (id == selected) {
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
