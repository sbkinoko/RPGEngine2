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
import core.menu.MenuItem
import values.Colors

@Composable
fun <T> Modifier.menuItem(
    id: T,
    menuItem: MenuItem<T>,
): Modifier {
    val selected by menuItem
        .selectedFlowState
        .collectAsState()

    return selectable(
        isSelected = id == selected,
    ).clickable {
        menuItem.onClick(id = id)
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
