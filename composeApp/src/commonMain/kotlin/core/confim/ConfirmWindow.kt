package core.confim

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.extension.pxToDp
import common.layout.CenterText
import common.values.Colors
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
                height.value = it.size.height / 3
                width.value = it.size.width / 3
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
                    .height(
                        height.value.pxToDp()
                    ),
            ) {
                CenterText(
                    modifier = equalAllocationModifier
                        .menuItem(
                            id = ConfirmViewModel.ID_YES,
                            childViewModel = confirmViewModel,
                        )
                        .background(color = Colors.MenuBackground),
                    text = "Yes",
                )

                CenterText(
                    modifier = equalAllocationModifier
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
