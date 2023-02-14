fun main(args: Array<String>) {
    val knight = constructKnightFromArguments(args)
    val path = knight.findShortestPath()

    if(path.point == Point(-1, -1)){
        println("-1")
        return
    }
    val pathList = path.toList()
    println(">> Path: ${getPrintablePath(pathList)}")
    println(">> Steps: ${pathList.size - 1}")

}