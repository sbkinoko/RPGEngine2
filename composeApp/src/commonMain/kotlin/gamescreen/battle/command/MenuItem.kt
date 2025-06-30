package gamescreen.battle.command

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.menuItem
import common.layout.CenterText
import core.menu.MenuItem

private const val size = 2

@Composable
fun <T> CommandMenu(
    itemList: List<T>,
    menuItem: MenuItem<T>,
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
                        menuItem = menuItem,
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
    menuItem: MenuItem<T>,
    modifier: Modifier = Modifier,
) {
    CenterText(
        modifier = modifier
            .menuItem(
                id = id,
                menuItem = menuItem,
            ),
        text = id.menuString,
    )
}
