package gamescreen.menu.item.repository.useitemid

interface UseItemIdRepository {
    var itemId: Int

    companion object {
        const val USE_ID_INIT = 0
    }
}
