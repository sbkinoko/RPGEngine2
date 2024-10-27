package gamescreen.menu.item.tool.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.layout.CenterText
import common.values.Colors
import gamescreen.menu.item.list.BagItemList

@Composable
fun BagItemList(
    itemList: BagItemList,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        itemList.bagItem
            .forEachIndexed { _, bagToolData ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .border(
                            width = 5.dp,
                            color = Colors.ControllerLine,
                            shape = RoundedCornerShape(
                                size = 5.dp
                            )
                        ),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    CenterText(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(4f),
                        text = itemList.getItemName(bagToolData.id),
                    )

                    CenterText(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        text = "x",
                    )

                    CenterText(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        text = bagToolData.num.toString(),
                    )
                }
            }
    }
}
