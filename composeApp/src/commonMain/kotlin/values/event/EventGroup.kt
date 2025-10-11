package values.event

sealed class EventGroup

sealed class EventGroup1 : EventGroup() {
    data object Girl : EventGroup1()
}

sealed class EventGroupBattle : EventGroup() {
    data object Monster : EventGroupBattle()
}

sealed class EventGroup2 : EventGroup() {
    data object Boy1 : EventGroup2()
    data object Boy2 : EventGroup2()
}
