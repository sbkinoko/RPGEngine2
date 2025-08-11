package gamescreen.map.repository.encouter

import values.GameParams
import kotlin.random.Random

class EncounterRepositoryImpl : EncounterRepository {
    private var judgeCount = 0
    private var totalDistance = 0f
    override fun judgeStartBattle(distance: Float): Boolean {
        totalDistance += distance
        if ((judgeCount + 1) * GameParams.ENCOUNTER_DISTANCE <= totalDistance) {
            judgeCount++
            return Random.nextInt(100) < GameParams.ENCOUNTER_PROBABILITY
        }
        return false
    }

    override fun resetCount() {
        judgeCount = 0
        totalDistance = 0f
    }
}
