package gamescreen.map.layout.npc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.extension.pxToDp
import gamescreen.map.domain.npc.NPCData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val imageBinder = ImageBinder()

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NPC(
    npcData: NPCData,
    screenRatio: Float,
) {
    Box {
        npcData.forEach { npc ->
            npc.eventRectangle.let {
                Image(
                    modifier = Modifier
                        .size(
                            width = (it.width * screenRatio).pxToDp(),
                            height = (it.height * screenRatio).pxToDp(),
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
