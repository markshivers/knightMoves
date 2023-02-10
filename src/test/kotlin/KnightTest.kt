import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class KnightTest {

    @Test
    fun `Get Knight Close to Destination`() {
        val knight = Knight(Point(20,20), Point(0,0))
        val path = knight.findPath()
        println("Arrived at destination in ${path.size} moves, $path")
    }

    @ParameterizedTest
    @MethodSource("destinations")
    fun `Test multiple paths`(destination: Point) {
        val knight = Knight(Point(0,0), destination)
        val path = knight.findPath()
        println("Arrived at destination in ${path.size} moves, $path")
    }

    companion object{
        @JvmStatic
        fun destinations() = listOf(
            Point(45,20),
            Point(5,20),
            Point(5,4),
            Point(45,20),
        )
    }
}