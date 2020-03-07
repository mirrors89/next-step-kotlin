package webserver.http

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

@TestInstance(Lifecycle.PER_CLASS)
internal class RequestLineTest {


    @Test
    fun parse() {
        val requestLine: RequestLine = RequestLine.parse("GET /users HTTP/1.1")
        assertThat(requestLine.method, Is.`is`(HttpMethod.GET))
        assertThat(requestLine.requestURL.path, Is.`is`("/users"))
        assertThat(requestLine.protocol, Is.`is`("HTTP/1.1"))
    }


    @Test
    fun parseParameter() {
        val requestLine = RequestLine
                .parse("GET /users/create?userId=keeseung&password=password&name=keeseung HTTP/1.1")
        assertThat(requestLine.method, Is.`is`(HttpMethod.GET))
        assertThat(requestLine.requestURL.path, Is.`is`("/users/create"))
        assertThat(requestLine.getQueryValue("userId"), Is.`is`("keeseung"))
        assertThat(requestLine.getQueryValue("password"), Is.`is`("password"))
        assertThat(requestLine.getQueryValue("name"), Is.`is`("keeseung"))
        assertThat(requestLine.protocol, Is.`is`("HTTP/1.1"))
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun duplicateParameter() {
        RequestLine.parse("GET /users?userId=keeseung&userId=keeseung HTTP/1.1")
    }


    @ParameterizedTest
    @MethodSource("provideStringsForRequestLine")
    fun parameterizedTest(urlString: String, method: HttpMethod, path: String, parameter: Map<String, String>, protocol: String) {
        val requestLine = RequestLine.parse(urlString)
        assertThat(requestLine.method, Is.`is`(method))
        assertThat(requestLine.getPath(), Is.`is`(path))


        assertThat(requestLine.getParameters().entries, everyItem(isIn((parameter.entries))))
        assertThat(requestLine.protocol, Is.`is`(protocol))
    }

    fun provideStringsForRequestLine(): Stream<Arguments> = Stream.of(
                Arguments.of("GET /users?userId=testUser&password=password&name=DreamPlus HTTP/1.1",
                        HttpMethod.GET, "/users", object : HashMap<String, String>() {
                    init {
                        put("userId", "testUser")
                        put("password", "password")
                        put("name", "DreamPlus")
                    }
                }, "HTTP/1.1"),
                Arguments.of("GET /users?userId=mirrors89&password=test&name=KeeSeung HTTP/2",
                        HttpMethod.GET, "/users", object : HashMap<String, String>() {
                    init {
                        put("userId", "mirrors89")
                        put("password", "test")
                        put("name", "KeeSeung")
                    }
                }, "HTTP/2"),
                Arguments.of("POST /users HTTP/1.1",
                        HttpMethod.POST, "/users", HashMap<String, String>(), "HTTP/1.1"))

}