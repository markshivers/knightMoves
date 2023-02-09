data class ChessBoard(
    val length: Int,
    val width: Int,
)

data class Point(
    val x: Int,
    val y: Int,
)

interface ChessPiece {
    val startingPoint: Point
    val destinationPoint: Point
    fun availableMovements(): List<Point>
}

class Knight(
    override val startingPoint: Point,
    override val destinationPoint: Point,
): ChessPiece {
    override fun availableMovements(): List<Point> {
        TODO("Not yet implemented")
    }
}