package common.layout.spinnbutton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun <T> SpinButton(
    spinButtonData: SpinButtonData<T>,
    modifier: Modifier = Modifier,
) {
    val data by spinButtonData.dataFlow.collectAsState()

    Column(
        modifier = modifier,
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                spinButtonData.onClickAdd()
            },
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
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = data.toString(),
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                spinButtonData.onClickDec()
            },
        ) {
            Text("↓")
        }
    }
}
