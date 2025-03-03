package values

import androidx.compose.runtime.mutableStateOf

class GameParams {
    companion object {
        const val ENCOUNTER_DISTANCE = 200
        const val ENCOUNTER_PROBABILITY = 30

        val showCollisionObject = mutableStateOf(
            false
        )
    }
}
