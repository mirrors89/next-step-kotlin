package webserver.http

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException


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
                .parse("GET /users?userId=keeseung&password=password&name=keeseung HTTP/1.1")
        assertThat(requestLine.method, Is.`is`(HttpMethod.GET))
        assertThat(requestLine.requestURL.path, Is.`is`("/users"))
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

}