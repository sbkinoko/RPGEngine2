package gamescreen.map.repository.encouter

interface EncounterRepository {
    fun judgeStartBattle(
        distance: Float
    ): Boolean

    fun resetCount()
}
