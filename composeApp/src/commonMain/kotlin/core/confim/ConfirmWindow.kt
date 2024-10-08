package core.confim

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import common.extension.menuItem
import common.extension.pxToDp
import common.layout.CenterText
import common.values.Colors
import common.values.LayoutConst
import org.koin.compose.koinInject

@Composable
fun ConfirmWindow(
    callBack: () -> Unit,
    modifier: Modifier = Modifier,
    confirmViewModel: ConfirmViewModel = koinInject(),
) {
    confirmViewModel.callBack = callBack

    val height = remember {
        mutableStateOf(0)
    }

    val width = remember {
        mutableStateOf(0)
    }

    val initFlg = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .clickable {
                confirmViewModel.onClickItem(ConfirmViewModel.ID_NO)
            }.onGloballyPositioned {
                height.value = it.size.height
                width.value = (it.size.width * LayoutConst.CHOICE_WIDTH).toInt()
                initFlg.value = true
            },
        contentAlignment = Alignment.Center,
    ) {
        if (initFlg.value) {
            Column(
                modifier = Modifier
                    .width(
                        width.value.pxToDp()
                    )
                    .wrapContentHeight(),
            ) {
                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height.value * LayoutConst.CHOICE_HEIGHT).pxToDp())
                        .menuItem(
                            id = ConfirmViewModel.ID_YES,
                            childViewModel = confirmViewModel,
                        )
                        .background(color = Colors.MenuBackground),
                    text = "Yes",
                )

                CenterText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((height.value * LayoutConst.CHOICE_HEIGHT).pxToDp())
                        .menuItem(
                            id = ConfirmViewModel.ID_NO,
                            childViewModel = confirmViewModel,
                        )
                        .background(color = Colors.MenuBackground),
                    text = "No",
                )
            }
        }
    }
}
