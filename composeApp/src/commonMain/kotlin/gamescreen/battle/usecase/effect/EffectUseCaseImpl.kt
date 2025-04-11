package gamescreen.battle.usecase.effect

import gamescreen.battle.repository.attackeffect.AttackEffectInfo
import gamescreen.battle.repository.attackeffect.AttackEffectRepository
import gamescreen.battle.repository.attackeffect.rateList
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

    private val effectInfo = attackEffectRepository.effectStateFlow

    private var flashJob: Job = CoroutineScope(Dispatchers.Default).launch { }

    private var effectJob = CoroutineScope(Dispatchers.Default).launch { }

    override fun invoke(
        target: Int,
    ) {
        updateAttackEffect(target)

        showEffect()
    }

    private fun updateAttackEffect(target: Int) {
        attackEffectRepository.setEffect(
            effectInfo.value.mapIndexed { ind, info ->
                if (ind == target) {
                    AttackEffectInfo(rateList.size + 1)
                } else {
                    info
                }
            }
        )
    }

    private fun updateFlashInfo(target: Int) {
        flashRepository.setInfo(flashInfo.value.mapIndexed { id, info ->
            if (id == target) {
                FlashInfo(6)
            } else {
                info
            }
        }
        )
    }

    private fun showEffect() {
        effectJob.cancel()

        effectJob = CoroutineScope(Dispatchers.Default).launch {
            while (
                effectInfo.value.any {
                    it.count > 0
                }
            ) {
                delay(50)
                attackEffectRepository.setEffect(
                    effectInfo.value.mapIndexed { id, it ->
                        if (it.count == 0) {
                            return@mapIndexed it
                        }
                        val newCount = it.count - 1

                        // 攻撃エフェクトが終わったら
                        if (newCount == 0) {
                            //点滅を開始
                            updateFlashInfo(target = id)
                            flash()
                        }
                        it.copy(
                            count = newCount
                        )
                    }
                )
            }
        }

        effectJob.start()
    }

    private fun flash() {
        flashJob.cancel()

        flashJob = CoroutineScope(Dispatchers.Default).launch {
            while (flashInfo.value.any {
                    // どれか1つでも点滅中なら
                    it.count > 0
                }) {
                flashRepository.setInfo(
                    flashInfo.value.mapIndexed { _, info ->
                        //　処理がないので元のデータを返す
                        if (info.count == 0) {
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
        flashJob.start()
    }
}
