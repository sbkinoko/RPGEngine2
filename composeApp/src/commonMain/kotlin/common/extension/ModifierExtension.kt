package common.extension

import androidx.compose.foundation.border
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
