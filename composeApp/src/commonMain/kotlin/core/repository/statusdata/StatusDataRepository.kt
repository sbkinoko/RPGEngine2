package core.repository.statusdata

import core.domain.status.StatusData
import core.domain.status.StatusType
import kotlinx.coroutines.flow.StateFlow

interface StatusDataRepository<T : StatusType> {

    val statusDataFlow: StateFlow<List<StatusData<T>>>

    fun getStatusData(id: Int): StatusData<T>

    fun setStatusData(
        id: Int,
        statusData: StatusData<T>,
    )

    fun getStatusList(): List<StatusData<T>>

    fun setStatusList(statusList: List<StatusData<T>>)
}
