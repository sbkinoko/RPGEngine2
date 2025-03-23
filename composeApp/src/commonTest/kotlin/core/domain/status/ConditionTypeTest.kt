package core.domain.status

val paralysis100 = ConditionType.Paralysis(
    probability = 100,
    cure = 100,
)

val paralysis50 = ConditionType.Paralysis(
    probability = 50,
    cure = 50,
)

val paralysis30 = ConditionType.Paralysis(
    probability = 30,
    cure = 30,
)

val damage100 = 100
val poison100 = ConditionType.Poison(
    damage = damage100,
    cure = 100,
)

val damage50 = 50
val poison50 = ConditionType.Poison(
    damage = damage50,
    cure = 50,
)

val poison0 = ConditionType.Poison(
    damage = 0,
    cure = 50,
)

val poisonCure70 = ConditionType.Poison(cure = 70)
val poisonCure40 = ConditionType.Poison(cure = 40)
