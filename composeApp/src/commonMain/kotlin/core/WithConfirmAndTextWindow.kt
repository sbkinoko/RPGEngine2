package core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors
import core.confim.ConfirmWindow
import core.text.TextWindow

@Composable
fun WithConfirmAndTextWindow(
    confirmCallBack: () -> Unit,
    textCallBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        content()

        ConfirmWindow(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Colors.OverlayMenu
                ),
            callBack = confirmCallBack,
        )

        TextWindow(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Colors.OverlayMenu
                ),
            callBack = textCallBack,
        )
    }
}
