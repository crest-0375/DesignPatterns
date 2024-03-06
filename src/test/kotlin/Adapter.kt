import org.assertj.core.api.Assertions
import kotlin.test.Test

class DisplayData(val index: Float, val data: String)

class DataDisplay {
    fun displayData(data: DisplayData) {
        println("Data is displayed: ${data.index} - ${data.data}")
    }
}

data class DatabaseData(val position: Int, val amount: Int)

class DatabaseGenerator {
    fun generateData(): List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(1, 11))
        list.add(DatabaseData(2, 22))
        list.add(DatabaseData(3, 33))
        return list
    }
}

interface DatabaseDataConverter {
    fun converterData(data: List<DatabaseData>): List<DisplayData>
}

class DataDisplayAdapter(private val display: DataDisplay) : DatabaseDataConverter {
    override fun converterData(data: List<DatabaseData>): List<DisplayData> {
        val returnList = arrayListOf<DisplayData>()
        for (i in data) {
            val ddt = DisplayData(i.position.toFloat(), i.amount.toString())
            display.displayData(ddt)
            returnList.add(ddt)
        }
        return returnList
    }
}

class AdapterTest {
    @Test
    fun adapterTest() {
        val generator = DatabaseGenerator()
        val generatedData = generator.generateData()
        val adapter = DataDisplayAdapter(DataDisplay())
        val convertedData = adapter.converterData(generatedData)
        Assertions.assertThat(convertedData.size).isEqualTo(3)
        Assertions.assertThat(convertedData[1].index).isEqualTo(2f)
        Assertions.assertThat(convertedData[1].data).isEqualTo("22")
    }
}