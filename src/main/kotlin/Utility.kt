import UtilityConstants.BOARD_SIZE_KEY
import UtilityConstants.DESTINATION_KEY
import UtilityConstants.SOURCE_KEY

object UtilityConstants {
    val BOARD_SIZE_KEY = "--board_size"
    val SOURCE_KEY = "--source"
    val DESTINATION_KEY = "--dest"
}

fun parseIntsFromString(argument: String): List<Int> {
    val integers = argument.split(",").map { it.toInt() }
    require(integers.size == 2) { throw NumberParsingException("Unable to construct pair of numbers from: $argument") }
    return integers
}

fun constructChessBoardFromString(argument: String): ChessBoard {
    val dimensions = parseIntsFromString(argument)
    return ChessBoard(dimensions[0], dimensions[1])
}

fun constructPointFromString(argument: String): Point {
    val coordinates = parseIntsFromString(argument)
    return Point(coordinates[0], coordinates[1])
}

fun constructKnightFromArguments(args: Array<String>): Knight {
    assertArguments(args)
    val chessBoard = constructChessBoardFromString(args[args.indexOf(BOARD_SIZE_KEY) + 1])
    val source = constructPointFromString(args[args.indexOf(SOURCE_KEY) + 1])
    val destination = constructPointFromString(args[args.indexOf(DESTINATION_KEY) + 1])

    require(chessBoard.containsPoint(source)) { throw InvalidStartingPointException("Starting Point must be contained within the board") }
    require(chessBoard.containsPoint(destination)) { throw InvalidDestinationException("Destination must be contained within the board") }

    return Knight(source, destination, chessBoard)
}

fun assertArguments(args: Array<String>) {
    require(args.contains(BOARD_SIZE_KEY)) { throw InvalidArgumentsException("Missing Board Size") }
    require(args.contains(SOURCE_KEY)) { throw InvalidArgumentsException("Missing Source Point") }
    require(args.contains(DESTINATION_KEY)) { throw InvalidArgumentsException("Missing Destination Point") }
    require(args.size == 6) { throw InvalidArgumentsException("Wrong Number of Arguments") }
}

fun getPrintablePath(points: List<Point>): String {
    val stringBuilder = StringBuilder()

    points.forEachIndexed { index, point ->
        stringBuilder.append("(${point.x},${point.y})")
        if (index != (points.size - 1)) {
            stringBuilder.append(" -> ")
        }
    }
    return stringBuilder.toString()
}