package webserver.http

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test


internal class RequestLineTest {


    @Test
    fun parse() {
        val requestLine: RequestLine = RequestLine.parse("GET /users HTTP/1.1")
        assertThat(requestLine.method, Is.`is`("GET"))
        assertThat(requestLine.requestURL, Is.`is`("/users"))
        assertThat(requestLine.protocol, Is.`is`("HTTP/1.1"))
    }
}