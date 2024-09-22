package core.confim

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.extension.equalAllocationModifier
import common.extension.menuItem
import common.layout.CenterText
import org.koin.compose.koinInject

@Composable
fun ConfirmWindow(
    callBack: () -> Unit,
    modifier: Modifier = Modifier,
    confirmViewModel: ConfirmViewModel = koinInject(),
) {
    confirmViewModel.callBack = callBack

    Box(
        modifier = modifier
            .clickable {
                confirmViewModel.onClickItem(ConfirmViewModel.ID_NO)
            },
        contentAlignment = Alignment.Center,
    ) {
        Column {
            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = ConfirmViewModel.ID_YES,
                        childViewModel = confirmViewModel,
                    ),
                text = "Yes",
            )

            CenterText(
                modifier = equalAllocationModifier
                    .menuItem(
                        id = ConfirmViewModel.ID_NO,
                        childViewModel = confirmViewModel,
                    ),
                text = "No",
            )
        }
    }
}
