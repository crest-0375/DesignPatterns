import kotlin.test.Test

interface Command {
    fun execute()
}

class OrderAddCommand(private val id: Long) : Command {
    override fun execute() {
        println("Adding for order with id $id")
    }
}

class OrderPayCommand(private val id: Long) : Command {
    override fun execute() {
        println("Paying for order with id $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()

    fun addInQueue(command: Command): CommandProcessor = apply { queue.add(command) }
    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

class CommandTest {
    @Test
    fun testCommand() {
        CommandProcessor().addInQueue(OrderAddCommand(1))
            .addInQueue(OrderAddCommand(2))
            .addInQueue(OrderAddCommand(3))
            .addInQueue(OrderPayCommand(2))
            .addInQueue(OrderPayCommand(1))
            .addInQueue(OrderPayCommand(3))
            .processCommands()
    }
}