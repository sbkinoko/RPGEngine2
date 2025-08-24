package core.repository.memory.character.statusdata

import core.domain.status.StatusData
import kotlinx.coroutines.flow.StateFlow

interface StatusDataRepository {

    val statusDataFlow: StateFlow<List<StatusData>>

    fun getStatusData(id: Int): StatusData

    fun setStatusData(
        id: Int,
        statusData: StatusData,
    )

    fun getStatusList(): List<StatusData>

    fun setStatusList(statusList: List<StatusData>)
}
