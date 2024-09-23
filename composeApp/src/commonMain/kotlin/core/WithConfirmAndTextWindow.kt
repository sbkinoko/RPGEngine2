package core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.values.Colors
import core.confim.ConfirmViewModel
import core.confim.ConfirmWindow
import core.text.TextViewModel
import core.text.TextWindow
import org.koin.compose.koinInject

@Composable
fun WithConfirmAndTextWindow(
    confirmCallBack: () -> Unit,
    textCallBack: () -> Unit,
    modifier: Modifier = Modifier,
    confirmViewModel: ConfirmViewModel = koinInject(),
    textViewModel: TextViewModel = koinInject(),
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        content()

        val confirmFlg = confirmViewModel.getShowStateAsState().value
        if (confirmFlg) {
            ConfirmWindow(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Colors.OverlayMenu
                    ),
                callBack = confirmCallBack,
            )
        }

        val textFlg = textViewModel.getShowStateAsState().value
        if (textFlg) {
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
}
