package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.layout.spinnbutton.SpinButton
import gamescreen.menushop.domain.amountdata.AmountData
import values.Colors

@Composable
fun AmountComponent(
    amountData: AmountData,
    buttonText: String,
    onClickBuy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selected by amountData
        .selected
        .collectAsState()

    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            ).border(
                width = 1.dp,
                color = Colors.MenuFrame,
            ).clickable { },
    ) {

        amountData
            .buttonDataList
            .mapIndexed { index, buttonData ->
                SpinButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                        .then(
                            if (selected == index) {
                                Modifier.border(
                                    width = 1.dp,
                                    color = Colors.SelectedMenu
                                )
                            } else {
                                Modifier
                            }
                        ),
                    spinButtonData = buttonData,
                )
            }

        Button(
            onClick = onClickBuy,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight(),
        ) {
            Text(
                text = buttonText,
            )
        }
    }
}
