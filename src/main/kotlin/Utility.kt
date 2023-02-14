fun parseIntsFromString(argument: String): List<Int> {
    val integers = argument.split(",").map { it.toInt() }
    require(integers.size == 2) { throw NumberParsingException("Unable to construct pair of numbers from: $argument") }
    return integers
}

fun constructChessBoardFromString(argument: String): ChessBoard {
    val integers = parseIntsFromString(argument)
    return ChessBoard(integers[0], integers[1])
}

fun constructPointFromString(argument: String): Point {
    val integers = parseIntsFromString(argument)
    return Point(integers[0], integers[1])
}

fun constructKnightFromArguments(args: Array<String>): Knight {
    val chessBoard = constructChessBoardFromString(args[args.indexOf("--board_size") + 1])
    val source = constructPointFromString(args[args.indexOf("--source") + 1])
    val destination = constructPointFromString(args[args.indexOf("--dest") + 1])

    require(chessBoard.containsPoint(source)) {throw InvalidStartingPointException("Starting Point must be contained within the board")}
    require(chessBoard.containsPoint(destination)) {throw InvalidDestinationException("Destination must be contained within the board")}

    return Knight(source, destination, chessBoard)
}

fun getPrintablePath(points: List<Point>): String {
    val stringBuilder = StringBuilder()

    points.forEachIndexed { index, point ->
        stringBuilder.append("(${point.x},${point.y})")
        if(index != (points.size - 1)) {
            stringBuilder.append(" -> ")
        }
    }
   return stringBuilder.toString()
}