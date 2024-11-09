package values

import androidx.compose.ui.unit.dp

class LayoutConst {
    companion object {
        private val paddingMedium = 10.dp
        private val paddingLarge = 20.dp

        // コントローラー
        val STICK_PADDING = paddingMedium
        val BUTTON_PADDING = paddingLarge

        const val STICK_WIGHT = 3f
        const val BUTTON_WIGHT = 1f

        const val STICK_SIZE_RATIO = 0.2f

        //　選択肢
        const val CHOICE_WIDTH = 0.4f
        const val CHOICE_HEIGHT = 0.2f

    }
}
