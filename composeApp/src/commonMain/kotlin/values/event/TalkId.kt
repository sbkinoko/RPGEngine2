package values.event

sealed class TalkEvent : EventType() {
    data object Talk1 : TalkEvent()
    data object Talk2 : TalkEvent()
}
