package gamescreen.menu.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import common.extension.menuItem
import common.layout.CenterText
import org.koin.compose.koinInject
import values.Colors

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    mainMenuViewModel: MainMenuViewModel = koinInject(),
) {
    val money by mainMenuViewModel
        .moneyStateFlow
        .collectAsState()

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
                        .menuItem(
                            id = index * 2,
                            childViewModel = mainMenuViewModel,
                        ),
                    text = pair.first.text,
                )

                pair.second?.let {
                    CenterText(
                        modifier = equalAllocationModifier
                            .menuItem(
                                id = index * 2 + 1,
                                childViewModel = mainMenuViewModel,
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

        Text(
            "所持金:$money"
        )
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
