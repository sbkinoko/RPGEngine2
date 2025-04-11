package gamescreen.battle.usecase.effect

import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.flash.FlashInfo
import gamescreen.battle.repository.flash.FlashRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EffectUseCaseImpl(
    private val attackEffectRepository: AttackEffectRepository,
    private val flashRepository: FlashRepository,
) : EffectUseCase {

    private val flashInfo = flashRepository.flashStateFlow

    init {
        CoroutineScope(Dispatchers.Default).launch {

        }
    }

    override fun invoke(
        target: Int,
    ) {
        // 今までのデータを利用して新しいデータを作成
        flashRepository.setInfo(flashInfo.value.mapIndexed { id, info ->
            if (id == target) {
                FlashInfo(6)
            } else {
                info
            }
        }
        )

        flash()
        attackEffectRepository.showEffect(target)
    }

    private var job: Job = CoroutineScope(Dispatchers.Default).launch { }

    private fun flash() {
        job.cancel()

        job = CoroutineScope(Dispatchers.Default).launch {
            while (flashInfo.value.any {
                    // どれか1つでも点滅中なら
                    it.count > 0
                }) {
                flashRepository.setInfo(
                    flashInfo.value.mapIndexed { _, info ->
                        //　処理がないので元のデータを返す
                        if (info.count <= 0) {
                            return@mapIndexed info
                        }

                        val newCount = info.count - 1

                        val newInfo = info.copy(
                            count = newCount
                        )
                        newInfo
                    }
                )
                delay(100)
            }
        }
        job.start()
    }
}
