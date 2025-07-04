package gamescreen.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.extension.pxToDp
import common.layout.CenterText
import org.koin.compose.koinInject
import values.Colors

@Composable
fun TextWindow(
    modifier: Modifier = Modifier,
    textViewModel: TextViewModel = koinInject(),
) {
    val textBoxData = textViewModel.showState.collectAsState()

    if (textBoxData.value == null) {
        return
    }

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
                    .padding(5.dp)
                    .height(height.value.pxToDp())
                    .fillMaxWidth()
                    .menuItem(
                        id = TextViewModel.INITIAL,
                        menuItem = textViewModel,
                    )
                    .background(color = Colors.MenuBackground)
                    .padding(5.dp),
                text = textViewModel.text,
            )
        }
    }
}
