package gamescreen.battle.command

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem2
import common.layout.CenterText

private const val size = 2

@Composable
fun <T> CommandMenu(
    itemList: List<T>,
    onClick2: OnClick2<T>,
    modifier: Modifier,
) where T : CommandType<T> {
    Column(modifier = modifier) {
        itemList.chunked(size).map { list ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                list.map {
                    MenuItem(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        id = it,
                        onClick2 = onClick2,
                    )
                }

                // sizeの個数がなければspacerを入れる
                repeat(size - list.size) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
fun <T : CommandType<T>> MenuItem(
    id: T,
    onClick2: OnClick2<T>,
    modifier: Modifier = Modifier,
) {
    CenterText(
        modifier = modifier
            .menuItem2(
                id = id,
                onClick2 = onClick2,
            ),
        text = id.menuString,
    )
}
