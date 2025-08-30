package core.repository.storage.player

interface PlayerDBRepository {

    fun setPlayers(players: List<Int>)

    fun getPlayers(): List<Int>
}
