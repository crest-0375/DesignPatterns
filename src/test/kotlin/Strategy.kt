import org.assertj.core.api.Assertions
import kotlin.test.Test

class Printer(private val stringFormatterStrategy: (String) -> String) {

    fun printString(message: String):String {
        return stringFormatterStrategy(message)
    }
}

val lowerCaseFormatter= {it:String -> it.lowercase()}
val upperCaseFormatter= {it:String -> it.uppercase()}

class StrategyTest{
    @Test
    fun testStrategy(){
        val inputString = "Lorem ipsum DOLOR sit amet"

        val lowerCasePrinter = Printer(lowerCaseFormatter)
        val lString = lowerCasePrinter.printString(inputString)

        val upperCasePrinter = Printer(upperCaseFormatter)
        val uString = upperCasePrinter.printString(inputString)

        Assertions.assertThat(uString).isEqualTo("LOREM IPSUM DOLOR SIT AMET")
        Assertions.assertThat(lString).isEqualTo("lorem ipsum dolor sit amet")
    }
}