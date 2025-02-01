package data.item.skill

sealed class SkillId {
    data object NONE : SkillId()
    data object CantUse : SkillId()

    data object AttackToTwo : SkillId()
    data object Normal1 : SkillId()
    data object Normal2 : SkillId()

    data object Heal : SkillId()
    data object Revive : SkillId()
}
