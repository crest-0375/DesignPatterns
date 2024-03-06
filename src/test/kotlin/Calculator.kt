import org.assertj.core.api.Assertions
import kotlin.test.Test

class Calculator {
    fun sum(a: Int, b: Int) = a + b
}
class CalculatorTest{
    @Test
    fun sum(){
val calc = Calculator()
        Assertions.assertThat(calc.sum(3,5)).isEqualTo(8)
    }
}