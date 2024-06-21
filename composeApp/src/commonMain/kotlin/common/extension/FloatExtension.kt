package common.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Float.pxToDp():Dp {
    val density = LocalDensity.current
    with(density){
        return this@pxToDp.toDp()
    }
}
