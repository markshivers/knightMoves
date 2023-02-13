import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KnightTest {


    @ParameterizedTest
    @MethodSource("startEndPointsBoardDimensions")
    fun `Test multiple paths`(startingSpace: Point, destination: Point, chessBoard: ChessBoard) {
        val knight = Knight(startingSpace, destination, chessBoard)
        val path = knight.findShortestPath()
        println("Arrived at destination in ${path.length()} moves, $path")
        assertFalse {  path.toList().any { it.x < 0 || it.y < 0 || it.x >= chessBoard.width || it.y >= chessBoard.height }}
        assertEquals(destination, path.point)
    }

    @Test
    fun `Already Visited Test`() {
        val pathNode = PathNode(Point(44,33), PathNode(Point(33,56),PathNode(Point(1,2))))
        assertTrue { pathNode.alreadyVisited(Point(1,2)) }
    }

//    @ParameterizedTest
//    @MethodSource("invalidStartEndPointsBoardDimensions")
//    fun `Test multiple invalid paths`(startingSpace: Point, destination: Point, xDimension: Int, yDimension: Int) {
//        val knight = Knight(startingSpace, destination)
//        val path = knight.findPath()
//        println("Arrived at destination in ${path.size} moves, $path")
//        assertEquals(Point(-1,-1), path.last())
//    }

    companion object {
        @JvmStatic
        fun startEndPointsBoardDimensions() = listOf(
            Arguments.of(Point(0, 0), Point(45, 20), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(5, 20), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(5, 4), ChessBoard(100, 100)),
            Arguments.of(Point(20, 20), Point(5, 4), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(45, 20), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(99, 99), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(98, 99), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(97, 99), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(96, 99), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(96, 1), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(99, 1), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(99, 0), ChessBoard(100, 100)),
            Arguments.of(Point(0, 0), Point(0, 99), ChessBoard(100, 100)),
            Arguments.of(Point(24, 31), Point(0, 99), ChessBoard(100, 100)),
        )

        @JvmStatic
        fun invalidStartEndPointsBoardDimensions() = listOf(
            Arguments.of(Point(0, 0), Point(45, 20), 2, 100),
            Arguments.of(Point(0, 0), Point(5, 20), 2, 100),
            Arguments.of(Point(0, 0), Point(5, 4), 2, 100),
            Arguments.of(Point(0, 0), Point(5, 4), 2, 100),
            Arguments.of(Point(0, 0), Point(45, 20), 2, 100),
        )
    }
}