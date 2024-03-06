import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

interface Datasource

class DatabaseDataSource : Datasource
class NetworkDataSource : Datasource

abstract class DataSourceFactory {
    abstract fun makeDataSource(): Datasource

    companion object {
        inline fun <reified T : Datasource> createFactory(): DataSourceFactory = when (T::class) {
            DatabaseDataSource::class -> DatabaseFactory()
            NetworkDataSource::class -> NetworkFactory()
            else -> throw IllegalArgumentException()
        }
    }
}

class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource(): NetworkDataSource = NetworkDataSource()
}

class DatabaseFactory : DataSourceFactory() {
    override fun makeDataSource(): Datasource = DatabaseDataSource()
}

class AbstractFactoryTest {
    @Test
    fun afTest() {
        val dataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val datasource = dataSourceFactory.makeDataSource()
        println("Created Datasource $datasource")

        Assertions.assertThat(datasource).isInstanceOf(DatabaseDataSource::class.java)
    }
}