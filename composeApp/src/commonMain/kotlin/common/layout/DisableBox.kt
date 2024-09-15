package common.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors

/**
 * 内容を表示するが、条件によって操作できなくするBox
 */
@Composable
fun DisableBox(
    isDisable: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        // 内容表示
        content()

        // タップできないように別の要素を重ねる
        if (isDisable) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clickable {

                    }.background(
                        color = Colors.Disabled
                    )
            ) {

            }
        }
    }
}
