package data.item.skill


// fixme enumにする
sealed class SkillId {
    data object NONE : SkillId()
    data object CantUse : SkillId()

    data object AttackToTwo : SkillId()
    data object Normal1 : SkillId()
    data object Normal2 : SkillId()

    data object Heal : SkillId()
    data object Revive : SkillId()

    data object Paralysis : SkillId()
    data object Poison : SkillId()

}
