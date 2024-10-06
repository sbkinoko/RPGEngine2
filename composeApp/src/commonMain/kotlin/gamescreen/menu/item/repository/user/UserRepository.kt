package gamescreen.menu.item.repository.user

interface UserRepository {
    var userId: Int

    companion object {
        const val USER_INIT = 0
    }
}
