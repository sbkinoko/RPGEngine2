package gamescreen.map.domain.background

import androidx.compose.runtime.Stable

@Stable
data class FrontObjectData(
    val fieldData: List<List<BackgroundCell?>>,
)

fun BackgroundData.toFrontData(): FrontObjectData =
    FrontObjectData(
        this.fieldData
    )
