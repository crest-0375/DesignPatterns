import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

object NetworkDriver {
    init {
        println("Initializing $this")
    }

    fun log(): NetworkDriver = apply { println("Network driver $this") }
}

class SingleTonTest {
    @Test
    fun testSingleTon() {
        println("Start")
        val networkDriver1 = NetworkDriver.log()
        val networkDriver2 = NetworkDriver.log()

        Assertions.assertThat(networkDriver1).isSameAs(NetworkDriver)
        Assertions.assertThat(networkDriver2).isSameAs(NetworkDriver)
    }
}