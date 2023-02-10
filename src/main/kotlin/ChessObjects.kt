import KnightMoves.*
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
        return availableDirections.values.map { it.add(currentPoint) }
    }

    private fun findNextGeneralDirectionMove(currentPoint1: Point): Point {
        val directionOfDestination = currentPoint1.findDirection(destinationPoint)
        return availableDirections.values.minByOrNull { it.findDirection(directionOfDestination).findMagnitude() }
            ?.add(currentPoint1)
            ?: throw RuntimeException("Unable to find next move")
    }

    private fun dialInPosition(): List<Point> {
        val nextMoves = mutableListOf<Point>()
        //Direction should now be something like (-1,1), (1,0) but always just 1s and 0s
        //TODO take into account the edge of the board
        val nextDirections = when (destinationPoint.findDirection(currentPoint)) {
            Point(0, 1) -> listOf(ONE_O_CLOCK, FOUR_O_CLOCK)
            Point(1, 1) -> listOf(FIVE_O_CLOCK)
            Point(1, 0) -> listOf(FOUR_O_CLOCK, SEVEN_O_CLOCK)
            Point(1, -1) -> listOf(SEVEN_O_CLOCK)
            Point(0, -1) -> listOf(FIVE_O_CLOCK, TWO_O_CLOCK)
            Point(-1, -1) -> listOf(FOUR_O_CLOCK)
            Point(-1, 0) -> listOf(EIGHT_O_CLOCK, FOUR_O_CLOCK)
            Point(-1, 1) -> listOf(SEVEN_O_CLOCK)
            else -> throw RuntimeException("Unable to determine next move")
        }
        nextDirections.map { it.point }.forEach {
            currentPoint = currentPoint.add(it)
            nextMoves.add(currentPoint)
        }
        currentPoint = destinationPoint
        nextMoves.add(destinationPoint)
        return nextMoves
    }

    fun findPath(): List<Point> {
        val path = mutableListOf<Point>()
        while (currentPoint != destinationPoint) {
//            println("currentPoint: $currentPoint")
            path.add(currentPoint)
            if ((destinationPoint.x - currentPoint.x).absoluteValue < 2 && (destinationPoint.y - currentPoint.y) < 2) {
                println("Got Close! current: $currentPoint, destination: $destinationPoint")
                path.addAll(dialInPosition())
                break
            }
            currentPoint = findNextGeneralDirectionMove(currentPoint)
            if (path.size > 1 && currentPoint == path[path.size - 2]) {
                println("Next move equals previous move")
                break
            }
        }
        return path
    }

    companion object {
        private val availableDirections = mapOf(
            ONE_O_CLOCK to Point(1, 2),
            TWO_O_CLOCK to Point(2, 1),
            FOUR_O_CLOCK to Point(2, -1),
            FIVE_O_CLOCK to Point(1, -2),
            SEVEN_O_CLOCK to Point(-1, -2),
            EIGHT_O_CLOCK to Point(-2, -1),
            TEN_O_CLOCK to Point(-2, 1),
            ELEVEN_O_CLOCK to Point(-1, 2),
        )
    }

}

//Knight moves broken down into positions around an analog clock
enum class KnightMoves(val point: Point) {
    ONE_O_CLOCK(Point(1, 2)),
    TWO_O_CLOCK(Point(2, 1)),
    FOUR_O_CLOCK(Point(2, -1)),
    FIVE_O_CLOCK(Point(1, -2)),
    SEVEN_O_CLOCK(Point(-1, -2)),
    EIGHT_O_CLOCK(Point(-2, -1)),
    TEN_O_CLOCK(Point(-2, 1)),
    ELEVEN_O_CLOCK(Point(-1, 2)),
}