import KnightMove.*
import Point.Companion.INVALID_POINT
import java.lang.RuntimeException
import kotlin.math.absoluteValue
import kotlin.math.sqrt

data class ChessBoard(
    val height: Int,
    val width: Int,
) {
    fun containsPoint(point: Point): Boolean {
        if(height == -1 && width == -1) {
            return (point.x >= 0) && (point.y >= 0)
        } else{
            return (point.x >= 0) && (point.x < width) && (point.y >= 0) && (point.y < height)
        }
    }
}

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

    fun findDistanceSquared(otherPoint: Point): Int {
        return x * otherPoint.x + y * otherPoint.y
    }

    companion object {
        val INVALID_POINT = Point(-1, -1)
    }


}

class Knight(
    private val startingPoint: Point,
    private val destinationPoint: Point,
    private val chessBoard: ChessBoard,
) {
    private val path = mutableListOf<Point>()
    private var currentPoint = startingPoint

    private fun findNextGeneralDirectionMove(currentPoint1: Point): Point {
        val directionOfDestination = currentPoint1.findDirection(destinationPoint)
        return availableDirections.values.minByOrNull { it.findDirection(directionOfDestination).findMagnitude() }
            ?.add(currentPoint1)
            ?: throw RuntimeException("Unable to find next move")
    }

    fun findShortestPath(): PathNode {
        val paths = mutableListOf<PathNode>()
        paths.add(PathNode(startingPoint))
        val visited = mutableSetOf<Point>()
        while (paths.isNotEmpty()) {
            val currentPathNode = paths.first()
            if (currentPathNode.point == destinationPoint) {
                return currentPathNode
            }
            paths.removeFirst()
            KnightMove.values().forEach {
                val nextPoint = currentPathNode.point.add(it.direction)
                if (chessBoard.containsPoint(nextPoint)){
                    if(!visited.contains(nextPoint)) {
                        paths.add(PathNode(nextPoint, currentPathNode))
                        visited.add(nextPoint)
                    }
                }
            }
        }
        return PathNode(Point(-1, -1))

    }

    fun findPath(): List<Point> {

        while (currentPoint != destinationPoint) {
//            println("currentPoint: $currentPoint")
            if ((destinationPoint.x - currentPoint.x).absoluteValue < 2 && (destinationPoint.y - currentPoint.y) < 2) {
                println("Got Close! current: $currentPoint, destination: $destinationPoint")
//                path.addAll(dialInPosition(getPreviousSpace(path)))
                break
            }
            currentPoint = findNextGeneralDirectionMove(currentPoint)
            if (path.size > 1 && currentPoint == path[path.size - 2]) {
                println("Next move equals previous move")
                path.add(INVALID_POINT)
                break
            }
            path.add(currentPoint)
        }
        return path
    }

//    private fun getPreviousSpace(path: MutableList<Point>): Point {
//        if (path.size > 1) {
//            return path[path.size - 2]
//        } else {
//            return path[0]
//        }
//    }

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
enum class KnightMove(val direction: Point) {
    ONE_O_CLOCK(Point(1, 2)),
    TWO_O_CLOCK(Point(2, 1)),
    FOUR_O_CLOCK(Point(2, -1)),
    FIVE_O_CLOCK(Point(1, -2)),
    SEVEN_O_CLOCK(Point(-1, -2)),
    EIGHT_O_CLOCK(Point(-2, -1)),
    TEN_O_CLOCK(Point(-2, 1)),
    ELEVEN_O_CLOCK(Point(-1, 2)),
}

data class PathNode(
    val point: Point,
    val previousPathNode: PathNode? = null,
) {
    fun alreadyVisited(point: Point): Boolean {
        return this.point == point || (previousPathNode?.alreadyVisited(point) == true)
    }

    fun didNotAlreadyVisit(point: Point) = !alreadyVisited(point)

    fun length(): Int {
        return 1 + (previousPathNode?.length() ?: 0)
    }

    fun toList(): List<Point> {
        var currentPathNode = this
        val pathList = mutableListOf(currentPathNode.point)

        while(currentPathNode.previousPathNode != null) {
            currentPathNode.previousPathNode?.let {
                currentPathNode = it
                pathList.add(currentPathNode.point)
            }
        }
        return pathList.reversed()
    }
}

fun doNOthing(directionToDestination: Point) = when (directionToDestination) {
    Point(0, 1) -> Pair(
        listOf(ONE_O_CLOCK, FIVE_O_CLOCK),
        listOf(ELEVEN_O_CLOCK, SEVEN_O_CLOCK)
    )

    Point(1, 1) -> Pair(listOf(FIVE_O_CLOCK), listOf(TEN_O_CLOCK))
    Point(1, 0) -> Pair(
        listOf(FOUR_O_CLOCK, SEVEN_O_CLOCK),
        listOf(TWO_O_CLOCK, TEN_O_CLOCK)
    )

    Point(1, -1) -> Pair(listOf(SEVEN_O_CLOCK), listOf(ONE_O_CLOCK))
    Point(0, -1) -> Pair(
        listOf(FIVE_O_CLOCK, TWO_O_CLOCK),
        listOf(FOUR_O_CLOCK, ONE_O_CLOCK)
    )

    Point(-1, -1) -> Pair(listOf(FOUR_O_CLOCK), listOf(ELEVEN_O_CLOCK))
    Point(-1, 0) -> Pair(
        listOf(EIGHT_O_CLOCK, FOUR_O_CLOCK),
        listOf(TEN_O_CLOCK, FIVE_O_CLOCK)
    )

    Point(-1, 1) -> Pair(listOf(SEVEN_O_CLOCK), listOf(TWO_O_CLOCK))
    else -> Pair(emptyList(), emptyList())
}