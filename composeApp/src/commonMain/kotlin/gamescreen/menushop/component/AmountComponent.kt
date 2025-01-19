package gamescreen.menushop.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gamescreen.menushop.amountdata.AmountData
import values.Colors
import values.TextData

@Composable
fun AmountComponent(
    amountData: AmountData,
    onClickBuy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val amount1 by amountData.amount1.collectAsState()
    val amount2 by amountData.amount2.collectAsState()

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
            amount = amount2,
            onClickAdd = {
                amountData.incAmount2()
            },
        )

        SpinButton(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            amount = amount1,
            onClickAdd = {
                amountData.incAmount1()
            },
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

@Composable
fun SpinButton(
    amount: Int,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickAdd,
        ) {
            Text("↑")
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = amount.toString(),
            )
        }

        Button(

            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text("↓")
        }
    }
}
