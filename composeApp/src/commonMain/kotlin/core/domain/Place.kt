package core.domain

enum class Place {
    MAP,
    BATTLE,
    BOTH,
    NEITHER,
    ;

    companion object {
        fun canUsePlace(
            here: Place,
            usablePlace: Place,
        ): Boolean {
            //　どちらでも使える
            if (usablePlace == BOTH) {
                return true
            }

            //　どちらでも使えない
            if (usablePlace == NEITHER) {
                return false
            }

            // 一致してたら使える
            return here == usablePlace
        }
    }
}
