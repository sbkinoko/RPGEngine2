package gamescreen.map.domain.background

import androidx.compose.runtime.Stable

@Stable
data class ObjectData(
    val fieldData: List<List<BackgroundCell?>>,
)
