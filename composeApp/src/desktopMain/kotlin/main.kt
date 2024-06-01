import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import layout.map.MapScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "RPGEngine") {
        MapScreen()
    }
}
