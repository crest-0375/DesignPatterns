import org.assertj.core.api.Assertions
import kotlin.test.Test

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(private val token: String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nAuthorization: $token".let {
            next?.addHeader(it) ?: it
        }
}

class ContentTypeHeader(private val contentType: String, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nContentType:  $contentType".let { next?.addHeader(it) ?: it }
}

class BodyPayLoadHeader(private val body: String, val next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) = "$inputHeader\n$body".let { next?.addHeader(it) ?: it }
}

class ChainOfResponsibilityTest {
    @Test
    fun testChainOfResponsibility() {
        val authenticationHeader = AuthenticationHeader("123456")
        val contentTypeHeader = ContentTypeHeader("json")
        val bodyPayLoadHeader = BodyPayLoadHeader("Body: {\"username\" = \"john\"}")

        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayLoadHeader
        val messageWithAuthentication = authenticationHeader.addHeader("Headers with authentication")
        println(messageWithAuthentication)
        println("-----------------------------------------")
        val messageWithoutAuthentication = contentTypeHeader.addHeader("Headers without authentication")
        println(messageWithoutAuthentication)

        Assertions.assertThat(messageWithAuthentication).isEqualTo(
            """
        Headers with authentication
        Authorization: 123456
        ContentType:  json
        Body: {"username" = "john"}
    """.trimIndent()
        )
        Assertions.assertThat(messageWithoutAuthentication).isEqualTo(
            """
        Headers without authentication
        ContentType:  json
        Body: {"username" = "john"}
    """.trimIndent()
        )
    }
}