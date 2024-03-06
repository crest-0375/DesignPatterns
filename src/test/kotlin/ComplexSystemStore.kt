import org.assertj.core.api.Assertions
import kotlin.test.Test
class ComplexSystemStore(private val filePath: String) {
    private val cache: HashMap<String, String>
    init {
        println("Reading from the file: $filePath")
        cache = HashMap()
    }
    fun store(key: String, value: String) {
        cache[key] = value
    }
    fun read(key: String) = cache[key] ?: ""
    fun commit() = println("Storing cached data to file $filePath")
}
data class User(val login: String)
class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/default.prefs")
    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.login)
        systemPreferences.commit()
    }
    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}
class FacadeTest {
    @Test
    fun testFacade() {
        val userRepo = UserRepository()
        val user = User("jhon")
        userRepo.save(user)
        val retrieveUser = userRepo.findFirst()
        Assertions.assertThat(retrieveUser.login).isEqualTo("jhon")
    }
}