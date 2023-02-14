data class ChessBoard(
    val width: Int,
    val height: Int,
) {
    init {
        if ((height < 0) xor (width < 0) || (height == 0 || width == 0)) {
            throw InvalidChessBoardDimensionsException("Unable to construct a chessboard of dimensions $width by $height")
        }
    }

    fun containsPoint(point: Point): Boolean {
        if (height < 0 && width < 0) {
            return (point.x >= 0) && (point.y >= 0)
        } else {
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
}

class Knight(
    private val startingPoint: Point,
    private val destinationPoint: Point,
    private val chessBoard: ChessBoard,
) {
    fun findShortestPath(): PathNode {
        if (!chessBoard.containsPoint(destinationPoint) || !chessBoard.containsPoint(startingPoint)) {
            return PathNode(Point(-1, -1))
        }
        val allPossiblePaths = mutableListOf<PathNode>()
        allPossiblePaths.add(PathNode(startingPoint))
        val visitedPoints = mutableSetOf<Point>()

        while (allPossiblePaths.isNotEmpty()) {
            val currentPathNode = allPossiblePaths.first()
            if (currentPathNode.point == destinationPoint) {
                // Since we are using a Breadth First Search the first path that gets to the destination
                return currentPathNode
            }
            //Removing the first path from the queue as it will either be used in the next created possible paths, or it will not reach the destination
            allPossiblePaths.removeFirst()

            KnightMove.values().forEach {
                val nextPoint = currentPathNode.point.add(it.direction)
                if (chessBoard.containsPoint(nextPoint)) {
                    if (!visitedPoints.contains(nextPoint)) {
                        allPossiblePaths.add(PathNode(nextPoint, currentPathNode))
                        visitedPoints.add(nextPoint)
                    }
                }
            }
        }
        return PathNode(Point(-1, -1))
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
    fun toList(): List<Point> {
        var currentPathNode = this
        val pathList = mutableListOf(currentPathNode.point)

        while (currentPathNode.previousPathNode != null) {
            currentPathNode.previousPathNode?.let {
                currentPathNode = it
                pathList.add(currentPathNode.point)
            }
        }
        return pathList.reversed()
    }
}