package data.event

import common.DefaultScope
import core.domain.status.job.Job
import core.repository.memory.character.player.PlayerCharacterRepository
import core.repository.memory.character.statusdata.StatusDataRepository
import gamescreen.choice.Choice
import gamescreen.choice.repository.ChoiceRepository
import gamescreen.text.TextBoxData
import gamescreen.text.repository.TextRepository
import kotlinx.coroutines.launch
import values.Constants
import values.event.EventJob

class TalkJob(
    private val textRepository: TextRepository,
    private val choiceRepository: ChoiceRepository,

    private val playerCharacterRepository: PlayerCharacterRepository,
    private val playerRepository: StatusDataRepository,
) : AbstractEventManager<EventJob>() {
    override val flgName: String
        get() = "test"

    private fun changeJob() {

        val list: MutableList<Choice> = mutableListOf()

        for (i in 0 until Constants.playerNum) {
            val status = playerRepository.getStatusList()[i]

            val job = playerCharacterRepository.getStatus(i)

            list += Choice(
                text = "${status.name}:${job.job.displayName}",
                callBack = {
                    val newJob =
                        when (job.job) {
                            Job.Warrior -> Job.Mage
                            Job.Mage -> Job.Warrior
                        }

                    DefaultScope.launch {
                        playerCharacterRepository.setStatus(
                            i,
                            job.copy(job = newJob)
                        )
                    }

                    textRepository.push(
                        TextBoxData(
                            text = "職業変えました"
                        )
                    )
                },
            )
        }

        list += Choice(
            text = "やっぱやめた",
            callBack = {
                textRepository.push(
                    textBoxData = TextBoxData("またお越しください")
                )
            }
        )

        textRepository.push(
            TextBoxData(
                text = "誰を変更しますか？",
                callBack = {
                    choiceRepository.push(
                        list,
                    )
                }
            )
        )
    }

    override fun callEvent(key: EventJob) {

        val talkData: List<TextBoxData> = listOf(
            TextBoxData(
                text = "職業を変えますか？",
                callBack = {
                    choiceRepository.push(
                        listOf(
                            Choice(
                                text = "はい",
                                callBack = {
                                    changeJob()
                                }
                            ),
                            Choice(
                                text = "いいえ",
                                callBack = {
                                    textRepository.push(
                                        TextBoxData(
                                            text = "またお越しください"
                                        )
                                    )
                                }
                            ),
                        )
                    )
                }
            )
        )

        textRepository.push(
            talkData,
        )
    }
}
