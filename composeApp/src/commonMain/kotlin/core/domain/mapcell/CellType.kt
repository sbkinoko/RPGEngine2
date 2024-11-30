package core.domain.mapcell

sealed class CellType {
    data object Glass : CellType()
    data object Water : CellType()
    data object Town1I : CellType()
    data object Town1O : CellType()
    data object Road : CellType()
    data object Box : CellType()
}
