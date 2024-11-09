package gamescreen.menu.usecase.givetool

import gamescreen.map.repository.player.PlayerRepository
import gamescreen.menu.MenuModule
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.repository.bag.BagRepository
import main.MainModule
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class GiveToolUseCaseImplTest : KoinTest {
    private val playerRepository: PlayerRepository by inject()
    private val bagRepository: BagRepository by inject()
    private val targetRepository: TargetRepository by inject()
    private val userRepository: UserRepository by inject()

    private val giveToolUseCase: GiveToolUseCase by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                MenuModule,
                MainModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }
}
