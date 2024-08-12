package menu.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.layout.CenterText
import common.values.Colors

@Composable
fun MainMenu(
    mainMenuViewModel: MainMenuViewModel,
    modifier: Modifier = Modifier,
) {
    val selected = mainMenuViewModel
        .getSelectedAsState().value

    Column(
        modifier = modifier
            .background(
                Colors.MenuBackground,
            )
            .padding(5.dp)
            .border(
                width = 2.dp,
                color = Colors.MenuFrame,
                shape = RectangleShape,
            )
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(
            5.dp,
        )
    ) {
        mainMenuViewModel.pairedList.forEachIndexed { index, pair ->
            Row(
                modifier = equalAllocationModifier,
                horizontalArrangement = Arrangement.spacedBy(
                    5.dp
                ),
            ) {
                CenterText(
                    modifier = equalAllocationModifier
                        .border(
                            width = 2.dp,
                            color = if (selected == index * 2) Colors.SelectedMenu
                            else Colors.MenuFrame,
                            shape = RectangleShape,
                        )
                        .clickable {
                            if (selected == index * 2) {
                                pair.first.onClick()
                            } else {
                                mainMenuViewModel.setSelected(
                                    index * 2
                                )
                            }

                        },
                    text = pair.first.text,
                )

                pair.second?.let {
                    CenterText(
                        modifier = equalAllocationModifier
                            .clickable {
                                if (selected == index * 2 + 1) {
                                    it.onClick()
                                } else {
                                    mainMenuViewModel.setSelected(
                                        index * 2 + 1
                                    )
                                }
                            }
                            .border(
                                width = 2.dp,
                                color = if (selected == index * 2 + 1) Colors.SelectedMenu
                                else Colors.MenuFrame,
                                shape = RectangleShape,
                            ),
                        text = it.text,
                    )
                } ?: run {
                    Spacer(
                        modifier = equalAllocationModifier,
                    )
                }
            }
        }
    }
}

val RowScope.equalAllocationModifier
    get() = Modifier
        .weight(1f)
        .fillMaxHeight()

val ColumnScope.equalAllocationModifier
    get() = Modifier
        .fillMaxHeight()
        .weight(1f)
