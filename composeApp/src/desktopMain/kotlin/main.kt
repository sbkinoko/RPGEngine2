import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import layout.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "RPGEngine") {
        App()
    }
}
