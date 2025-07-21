package gamescreen.menu.usecase.usetoolinmap

import core.usecase.item.useitem.UseItemUseCase
import gamescreen.menu.item.repository.index.IndexRepository
import gamescreen.menu.item.repository.target.TargetRepository
import gamescreen.menu.item.repository.user.UserRepository
import gamescreen.menu.repository.menustate.MenuStateRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository

class UseItemInMapUseCaseImpl(
    private val menuStateRepository: MenuStateRepository,

    private val textRepository: TextRepository,

    private val userRepository: UserRepository,
    private val indexRepository: IndexRepository,
    private val targetRepository: TargetRepository,
    private val useItemUseCase: UseItemUseCase,
) : UseItemInMapUseCase {
    override suspend fun invoke() {

        val item = useItemUseCase.invoke(
            userId = userRepository.userId,
            index = indexRepository.index,
            targetId = targetRepository.target,
        )

        textRepository.push(
            TextBoxData(
                text = "${item.name}を使った",
                callBack = { menuStateRepository.pop() }
            )
        )
    }
}
