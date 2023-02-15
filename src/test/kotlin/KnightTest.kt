import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class KnightTest {

    @ParameterizedTest
    @MethodSource("validPathTestScenarios")
    fun `Test multiple paths`(startingSpace: Point, destination: Point, chessBoard: ChessBoard, expectedPathSize: Int) {
        val knight = Knight(startingSpace, destination, chessBoard)
        val path = knight.findShortestPath()
        assertEquals(expectedPathSize, path.toList().size - 1)
        assertEquals(destination, path.point)
    }

    @ParameterizedTest
    @MethodSource("invalidPathTestScenarios")
    fun `Test multiple invalid paths`(startingSpace: Point, destination: Point, chessBoard: ChessBoard) {
        val knight = Knight(startingSpace, destination, chessBoard)
        val path = knight.findShortestPath()
        assertEquals(Point(-1, -1), path.point)
    }

    companion object {
        @JvmStatic
        fun validPathTestScenarios() = listOf(
            Arguments.of(Point(0, 0), Point(45, 20), ChessBoard(100, 100), 23),
            Arguments.of(Point(0, 0), Point(5, 20), ChessBoard(100, 100), 11),
            Arguments.of(Point(0, 0), Point(5, 4), ChessBoard(100, 100), 3),
            Arguments.of(Point(20, 20), Point(5, 4), ChessBoard(100, 100), 11),
            Arguments.of(Point(0, 0), Point(45, 20), ChessBoard(100, 100), 23),
            Arguments.of(Point(0, 0), Point(99, 99), ChessBoard(100, 100), 66),
            Arguments.of(Point(0, 0), Point(98, 99), ChessBoard(100, 100), 67),
            Arguments.of(Point(0, 0), Point(97, 99), ChessBoard(100, 100), 66),
            Arguments.of(Point(0, 0), Point(96, 99), ChessBoard(100, 100), 65),
            Arguments.of(Point(0, 0), Point(96, 1), ChessBoard(100, 100), 49),
            Arguments.of(Point(0, 0), Point(99, 1), ChessBoard(100, 100), 50),
            Arguments.of(Point(0, 0), Point(99, 0), ChessBoard(100, 100), 51),
            Arguments.of(Point(0, 0), Point(0, 99), ChessBoard(100, 100), 51),
            Arguments.of(Point(24, 31), Point(0, 99), ChessBoard(100, 100), 34),
            Arguments.of(Point(24, 31), Point(0, 99), ChessBoard(-1, -1), 34),
            Arguments.of(Point(24, 31), Point(234, 321), ChessBoard(-1, -1), 168),
            Arguments.of(Point(0, 0), Point(1, 0), ChessBoard(3, 3), 3),
            Arguments.of(Point(0, 0), Point(1, 0), ChessBoard(4, 4), 3),
        )

        @JvmStatic
        fun invalidPathTestScenarios() = listOf(
            Arguments.of(Point(0, 0), Point(45, 20), ChessBoard(2, 100)),
            Arguments.of(Point(0, 0), Point(5, 20), ChessBoard(2, 100)),
            Arguments.of(Point(0, 0), Point(5, 20), ChessBoard(200, 10)),
            Arguments.of(Point(0, 0), Point(5, 4), ChessBoard(2, 100)),
            Arguments.of(Point(0, 0), Point(45, 20), ChessBoard(2, 100)),
            Arguments.of(Point(0, 0), Point(1, 0), ChessBoard(2, 2)),
        )
    }
}