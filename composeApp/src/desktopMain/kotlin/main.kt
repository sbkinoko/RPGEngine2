
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import main.MainScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "RPGEngine") {
        MainScreen()
    }
}
