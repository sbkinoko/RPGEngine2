package core.repository.statusdata

import core.domain.status.StatusData
import core.domain.status.StatusType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatusDataRepositoryImpl<T : StatusType> : StatusDataRepository<T> {
    private val mutableStatusData: MutableStateFlow<List<StatusData<T>>> = MutableStateFlow(
        emptyList()
    )

    override val statusDataFlow: StateFlow<List<StatusData<T>>>
        get() = mutableStatusData.asStateFlow()

    override fun getStatusData(id: Int): StatusData<T> {
        return statusDataFlow.value[id]
    }

    override fun setStatusData(
        id: Int,
        statusData: StatusData<T>,
    ) {
        val list = statusDataFlow.value.toMutableList()

        list[id] = statusData

        mutableStatusData.value = list
    }

    override fun getStatusList(): List<StatusData<T>> {
        return statusDataFlow.value
    }

    override fun setStatusList(statusList: List<StatusData<T>>) {
        mutableStatusData.value = statusList
    }
}
