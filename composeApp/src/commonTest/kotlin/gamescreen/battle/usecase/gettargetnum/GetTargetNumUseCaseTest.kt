package battle.usecase.gettargetnum

import battle.BattleModule
import battle.domain.ActionType
import battle.repository.action.ActionRepository
import core.CoreModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetTargetNumUseCaseTest : KoinTest {

    private val getTargetNumUseCase: GetTargetNumUseCase by inject()

    private val actionRepository: ActionRepository by inject()

    @BeforeTest
    fun beforeTest() {
        startKoin {
            modules(
                BattleModule,
                CoreModule,
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun normal() {
        val playerId = 0
        val actionType = ActionType.Normal

        actionRepository.setAction(
            playerId = playerId,
            actionType = actionType,
        )

        getTargetNumUseCase(
            playerId = playerId,
        ).let {
            assertEquals(
                expected = 1,
                actual = it,
            )
        }
    }

    @Test
    fun skill() {
        val playerId = 1
        val actionType = ActionType.Skill

        actionRepository.setAction(
            playerId = playerId,
            actionType = actionType,
            skillId = 0
        )

        getTargetNumUseCase(
            playerId = playerId,
        ).let {
            assertEquals(
                expected = 2,
                actual = it,
            )
        }
    }
}