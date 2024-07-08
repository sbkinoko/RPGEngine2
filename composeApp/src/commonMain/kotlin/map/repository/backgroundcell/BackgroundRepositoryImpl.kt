package map.repository.backgroundcell

import kotlinx.coroutines.flow.MutableSharedFlow
import map.domain.BackgroundCell

class BackgroundRepositoryImpl : BackgroundRepository {
    override val backgroundFlow: MutableSharedFlow<List<List<BackgroundCell>>>
        get() = TODO("Not yet implemented")
    override var background: List<List<BackgroundCell>> = BackgroundRepository.initialBackground

    override fun getBackgroundAt(x: Int, y: Int): BackgroundCell {
        TODO("Not yet implemented")
    }

    override suspend fun setBackground(background: List<List<BackgroundCell>>) {
        TODO("Not yet implemented")
    }

    override suspend fun reload() {
        TODO("Not yet implemented")
    }
}
