package core.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.CenterText
import common.values.Colors
import org.koin.compose.koinInject

@Composable
fun TextWindow(
    callBack: () -> Unit,
    modifier: Modifier = Modifier,
    textViewModel: TextViewModel = koinInject(),
) {
    textViewModel.callBack = callBack

    val confirmFlg = textViewModel.getShowStateAsState().value

    val height = remember {
        mutableStateOf(0)
    }

    val width = remember {
        mutableStateOf(0)
    }

    val initFlg = remember {
        mutableStateOf(false)
    }

    if (confirmFlg) {
        Box(
            modifier = modifier
                .clickable {
                    textViewModel.onClickItem(TextViewModel.INITIAL)
                }.onGloballyPositioned {
                    height.value = it.size.height / 3
                    width.value = it.size.width
                    initFlg.value = true
                },
            contentAlignment = Alignment.BottomCenter,
        ) {
            if (initFlg.value) {
                CenterText(
                    modifier = Modifier
                        .menuItem(
                            id = TextViewModel.INITIAL,
                            childViewModel = textViewModel,
                        )
                        .background(color = Colors.MenuBackground)
                        .padding(5.dp),
                    text = textViewModel.text,
                )
            }
        }
    }
}
