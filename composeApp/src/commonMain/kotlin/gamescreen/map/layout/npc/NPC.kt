package gamescreen.map.layout.npc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import gamescreen.map.viewmodel.MapViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NPC(
    mapViewModel: MapViewModel,
    screenRatio: Float,
) {
    val npc by mapViewModel
        .npcFlow
        .collectAsState()

    val imageBinder = ImageBinderNPC()

    Box {
        npc.forEach { npc ->
            npc.eventSquare.let {
                Image(
                    modifier = Modifier
                        .size(
                            (it.size * screenRatio).pxToDp()
                        )
                        .offset(
                            x = (it.baseX * screenRatio).pxToDp(),
                            y = (it.baseY * screenRatio).pxToDp(),
                        ),
                    painter = painterResource(
                        imageBinder.bind(
                            npcType = npc.npcType,
                        )
                    ),
                    contentDescription = "NPC"
                )
            }
        }
    }
}
