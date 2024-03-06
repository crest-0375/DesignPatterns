import kotlin.test.Test

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine : CoffeeMachine {
    override fun makeSmallCoffee() {
        println("Normal coffee machine making small coffee.")
    }

    override fun makeLargeCoffee() {
        println("Normal coffee machine making large coffee.")
    }
}

// Decorator
class EnhancedCoffeeMachine(private val coffeeMachine: CoffeeMachine) : CoffeeMachine by coffeeMachine {
    override fun makeLargeCoffee() {
        println("Enhanced coffee machine: Making large coffee.")
    }
    // Extending behaviour

    fun makeMilkCoffee() {
        println("Enhanced coffee machine making milk coffee.")
        coffeeMachine.makeSmallCoffee()
        println("Enhanced coffee machine adding milk in it.")
    }
}

class DecoratorTest {
    @Test
    fun testDecorator() {
        val normalCoffeeMachine = NormalCoffeeMachine()
        val enhancedCoffeeMachine = EnhancedCoffeeMachine(normalCoffeeMachine)

        enhancedCoffeeMachine.makeLargeCoffee()
        println("---------------------")
        enhancedCoffeeMachine.makeMilkCoffee()
        println("---------------------")
        enhancedCoffeeMachine.makeSmallCoffee()
    }
}