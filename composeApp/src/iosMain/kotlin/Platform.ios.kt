//import platform.UIKit.UIDevice
////
class IOSPlatform: Platform {
    override val name: String = "test"
//        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()