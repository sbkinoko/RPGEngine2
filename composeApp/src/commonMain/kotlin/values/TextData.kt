package values

class TextData {
    companion object {
        const val HAS_FULL_ITEM = "のアイテムがいっぱいです"

        const val NEED_EXP_MAX_LEVEL = "最大レベル"


        const val BATTLE_MAIN_ATTACK = "攻撃"
        const val BATTLE_MAIN_ESCAPE = "逃げる"
    }

    object BattleFinishMoney {
        fun getText(money: Int): String {
            return "お金を $money 手に入れた"
        }
    }

    object BattleFinishExp {
        fun getText(exp: Int): String {
            return "expを $exp 手に入れた"
        }
    }

    object BattleFinishTool {
        fun getText(name: String): String {
            return "$name を手に入れた"
        }
    }

    object BattleFinishLevelUp {
        fun getText(name: String): String {
            return "$name はレベルアップした"
        }
    }
}
