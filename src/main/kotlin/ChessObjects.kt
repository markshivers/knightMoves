import java.lang.RuntimeException
import kotlin.math.absoluteValue
import kotlin.math.sqrt

data class ChessBoard(
    val length: Int,
    val width: Int,
)

data class Point(
    val x: Int,
    val y: Int,
) {
    fun add(otherPoint: Point): Point {
        return Point(this.x + otherPoint.x, this.y + otherPoint.y)
    }

    fun findDirection(otherPoint: Point): Point {
        return Point(otherPoint.x - this.x, otherPoint.y - this.y)
    }

    fun findSlope(otherPoint: Point): Double {
        //TODO protect zero
        return (otherPoint.y - this.y) / (otherPoint.x - this.x).toDouble()
    }

    fun findMagnitude(): Double {
        return sqrt(y * y.toDouble() + x * x)
    }


}

class Knight(
    private val startingPoint: Point,
    private val destinationPoint: Point,
) {
    private var currentPoint = startingPoint

    fun availableMoves(): List<Point> {
        return availableDirections.map { it.add(currentPoint) }
    }

    private fun findNextMove(currentPoint1: Point): Point {
        val direction = currentPoint1.findDirection(destinationPoint)
        return availableDirections.minByOrNull { it.findDirection(direction).findMagnitude() }?.add(currentPoint1)
            ?: throw RuntimeException("Unable to find next move")
    }

    fun findPath(): List<Point> {
        val path = mutableListOf<Point>()
        while (currentPoint != destinationPoint) {
            println("currentPoint: $currentPoint")
            path.add(currentPoint)
            if ((destinationPoint.x - currentPoint.x).absoluteValue < 2 || (destinationPoint.y - currentPoint.y) < 2) {
                println("Got Close! current: $currentPoint, destination: $destinationPoint")
                break
            }
            currentPoint = findNextMove(currentPoint)
            if (path.size > 1 && currentPoint == path[path.size - 2]) {
                println("Next move equals previous move")
                break
            }
        }
        return path
    }

    companion object {
        private val availableDirections = listOf(
            Point(2, 1),
            Point(2, -1),
            Point(1, 2),
            Point(1, -2),
            Point(-2, 1),
            Point(-2, -1),
            Point(-1, 2),
            Point(-1, -2),
        )
    }

}