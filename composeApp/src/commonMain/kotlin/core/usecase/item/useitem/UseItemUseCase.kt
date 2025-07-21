package core.usecase.item.useitem

import core.domain.item.Item

interface UseItemUseCase {
    suspend fun invoke(
        userId: Int,
        index: Int,
        targetId: Int,
    ): Item
}
