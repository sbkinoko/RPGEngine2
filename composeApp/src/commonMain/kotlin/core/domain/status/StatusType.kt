package core.domain.status

sealed class StatusType {
    data object Player : StatusType()
    data object Enemy : StatusType()
    data object None : StatusType()
}
