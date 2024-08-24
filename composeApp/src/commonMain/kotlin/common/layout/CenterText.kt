package common.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier.wrapContentSize()
            // todo 文字サイズの自動調整を実装
            // https://zenn.dev/warahiko/articles/971f1ff0591f62
        )
    }
}
