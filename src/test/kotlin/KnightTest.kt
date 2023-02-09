import org.junit.jupiter.api.Test

class KnightTest {

    @Test
    fun `Get Knight Close to Destination`() {
        val knight = Knight(Point(0,0), Point(20,20))
        val path = knight.findPath()
        println("Got close in ${path.size} moves, $path")
    }
}