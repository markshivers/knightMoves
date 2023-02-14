import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertNotNull

class UtilityTest {

    @ParameterizedTest
    @MethodSource("invalidStartingPointArguments")
    fun `Invalid starting points throws exception`(args: Array<String>) {
        assertThatThrownBy {
            constructKnightFromArguments(args)
        }.isExactlyInstanceOf(InvalidStartingPointException::class.java)
    }

    @ParameterizedTest
    @MethodSource("invalidDestinationArguments")
    fun `Invalid destinations throws exception`(args: Array<String>) {
        assertThatThrownBy {
            constructKnightFromArguments(args)
        }.isExactlyInstanceOf(InvalidDestinationException::class.java)
    }

    @ParameterizedTest
    @MethodSource("invalidChessBoardDimensionsArguments")
    fun `Invalid chess board dimensions throws exception`(args: Array<String>) {
        assertThatThrownBy {
            constructKnightFromArguments(args)
        }.isExactlyInstanceOf(InvalidChessBoardDimensionsException::class.java)
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    fun `Valid arguments succeed`(args: Array<String>) {
        assertNotNull(constructKnightFromArguments(args))
    }

    companion object {
        @JvmStatic
        fun invalidStartingPointArguments() = listOf(
            Arguments.of(arrayOf("--board_size", "100,100", "--source", "400,5", "--dest", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,100", "--source", "-2,5", "--dest", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,100", "--source", "2,-5", "--dest", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,100", "--source", "2,500", "--dest", "32,99")),
        )

        @JvmStatic
        fun invalidDestinationArguments() = listOf(
            Arguments.of(arrayOf("--board_size", "100,100", "--dest", "400,5", "--source", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,100", "--dest", "-2,5", "--source", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,100", "--dest", "2,-5", "--source", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,100", "--dest", "2,500", "--source", "32,99")),
        )

        @JvmStatic
        fun invalidChessBoardDimensionsArguments() = listOf(
            Arguments.of(arrayOf("--board_size", "-100,100", "--dest", "400,5", "--source", "32,99")),
            Arguments.of(arrayOf("--board_size", "100,-100", "--dest", "-2,5", "--source", "32,99")),
            Arguments.of(arrayOf("--board_size", "0,0", "--dest", "2,-5", "--source", "32,99")),
        )

        @JvmStatic
        fun validArguments() = listOf(
            Arguments.of(arrayOf("--board_size", "-100,-100", "--dest", "400,5", "--source", "32,99")),
            Arguments.of(arrayOf("--board_size", "-1,-1", "--dest", "400,5", "--source", "32,99")),
        )
    }
}