package common.extension

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.values.Colors

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
