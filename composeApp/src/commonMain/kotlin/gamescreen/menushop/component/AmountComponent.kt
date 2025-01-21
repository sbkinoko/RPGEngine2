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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.layout.spinnbutton.SpinButton
import gamescreen.menushop.domain.amountdata.AmountData
import values.Colors
import values.TextData

@Composable
fun AmountComponent(
    amountData: AmountData,
    onClickBuy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = Colors.MenuBackground,
            ).border(
                width = 1.dp,
                color = Colors.MenuFrame,
            ).clickable { },
    ) {

        SpinButton(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            spinButtonData = amountData.buttonData10,
        )

        SpinButton(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            spinButtonData = amountData.buttonData1,
        )

        Button(
            onClick = onClickBuy,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight(),
        ) {
            Text(
                text = TextData.SHOP_BUY
            )
        }
    }
}
