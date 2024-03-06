import org.assertj.core.api.Assertions
import kotlin.test.Test

sealed class AuthorizationState

data object UnAuthorized : AuthorizationState()

class Authorized(val userName: String) : AuthorizationState()

class AuthorizationPresenter {
    private var state: AuthorizationState = UnAuthorized

    val isAuthorized: Boolean
        get() = when (state) {
            is Authorized -> true
            is UnAuthorized -> false
        }

    val userName: String
        get() {
            return when (val state = this.state) {
                is Authorized -> state.userName
                is UnAuthorized -> "Unknown"
            }
        }

    fun loginUser(userName: String) {
        state = Authorized(userName)
    }

    fun logOut() {
        state = UnAuthorized
    }

    override fun toString(): String {
        return "User $userName is logged in: $isAuthorized"
    }
}

class StateTest {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()
        authorizationPresenter.loginUser("Admin")

        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(true)
        Assertions.assertThat(authorizationPresenter.userName).isEqualTo("Admin")

        authorizationPresenter.logOut()

        println(authorizationPresenter)
        Assertions.assertThat(authorizationPresenter.isAuthorized).isEqualTo(false)
        Assertions.assertThat(authorizationPresenter.userName).isEqualTo("Unknown")
    }
}