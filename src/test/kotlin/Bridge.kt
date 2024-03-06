import org.assertj.core.api.Assertions
import kotlin.test.Test

interface Device {
    var volume: Int
    fun getName(): String
}

class Radio : Device {
    override var volume = 0

    override fun getName() = "Radio $this"
}

class Television : Device {
    override var volume = 0

    override fun getName() = "TV - $this"
}

interface Remote {
    fun volumeUp()
    fun volumeDown()
}

class BasicRemote(private val device: Device) : Remote {
    override fun volumeUp() {
        device.volume++
        println("${device.getName()} volume up = ${device.volume}")
    }

    override fun volumeDown() {
        device.volume--
        println("${device.getName()} volume down = ${device.volume}")
    }
}

class BridgeTest {
    @Test
    fun testBridge() {
        val tv = Television()
        val radio = Radio()

        val tvRemote = BasicRemote(tv)
        val radioRemote = BasicRemote(radio)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeUp()
        radioRemote.volumeDown()

        Assertions.assertThat(tv.volume).isEqualTo(1)
        Assertions.assertThat(radio.volume).isEqualTo(2)
    }
}