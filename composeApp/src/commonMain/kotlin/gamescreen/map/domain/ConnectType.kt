package gamescreen.map.domain

enum class ConnectType {
    // 直線
    Horizontal,
    Vertical,

    // 十字路
    Cross,

    //　曲がり角
    LeftTopUp,
    LeftTopDown,
    RightToUp,
    RightToDown,

    // 三叉路
    WithoutLeft,
    WithoutRight,
    WithoutUp,
    WithoutDown,
}
