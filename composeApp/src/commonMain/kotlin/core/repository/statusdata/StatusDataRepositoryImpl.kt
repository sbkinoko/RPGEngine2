package core.repository.statusdata

import core.domain.status.StatusData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatusDataRepositoryImpl : StatusDataRepository {
    private val mutableStatusData: MutableStateFlow<List<StatusData>> = MutableStateFlow(emptyList())

    override val statusDataFlow: StateFlow<List<StatusData>>
        get() = mutableStatusData.asStateFlow()

    override fun getStatusData(id: Int): StatusData {
        return statusDataFlow.value[id]
    }

    override fun setStatusData(
        id: Int,
        statusData: StatusData,
    ) {
        val list = statusDataFlow.value.toMutableList()

        list[id] = statusData

        mutableStatusData.value = list
    }

    override fun getStatusList(): List<StatusData> {
        return statusDataFlow.value
    }

    override fun setStatusList(statusList: List<StatusData>) {
        mutableStatusData.value = statusList
    }
}
