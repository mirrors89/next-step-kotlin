package util

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import util.HttpRequestUtils.Companion.parseQueryString

class HttpRequestUtilsTest {

    @Test
    fun parseQueryString() {
        var queryString = "userId=javajigi"
        var parameters: Map<String, String> = parseQueryString(queryString)
        assertThat(parameters["userId"], Is.`is`("javajigi"))
        assertThat(parameters["password"], Is.`is`(CoreMatchers.nullValue()))
        queryString = "userId=javajigi&password=password2"
        parameters = parseQueryString(queryString)
        assertThat(parameters["userId"], Is.`is`("javajigi"))
        assertThat(parameters["password"], Is.`is`("password2"))
    }

    @Test
    fun parseQueryStringEmpty() {
        var parameters: Map<String, String> = parseQueryString("")
        assertThat(parameters.isEmpty(), Is.`is`(true))
        parameters = parseQueryString("")
        assertThat(parameters.isEmpty(), Is.`is`(true))
        parameters = parseQueryString(" ")
        assertThat(parameters.isEmpty(), Is.`is`(true))
    }

    @Test
    fun parseQueryString_invalid() {
        val queryString = "userId=javajigi&password"
        val parameters = parseQueryString(queryString)
        assertThat(parameters["userId"], Is.`is`("javajigi"))
        assertThat(parameters["password"], Is.`is`(CoreMatchers.nullValue()))
    }

    @Test
    fun parseCookies() {
        val cookies = "logined=true; JSessionId=1234"
        val parameters = HttpRequestUtils.parseCookies(cookies)
        assertThat(parameters["logined"], Is.`is`("true"))
        assertThat(parameters["JSessionId"], Is.`is`("1234"))
        assertThat(parameters["session"], Is.`is`(CoreMatchers.nullValue()))
    }

    @Test
    @Throws(Exception::class)
    fun keyValue() {
        val pair = HttpRequestUtils.getKeyValue("userId=javajigi", "=")
        assertThat(pair, Is.`is`(HttpRequestUtils.Pair("userId", "javajigi")))
    }

    @Test
    @Throws(Exception::class)
    fun keyValue_invalid() {
        val pair = HttpRequestUtils.getKeyValue("userId", "=")
        assertThat(pair, Is.`is`(CoreMatchers.nullValue()))
    }

    @Test
    @Throws(Exception::class)
    fun parseHeader() {
        val header = "Content-Length: 59"
        val pair = HttpRequestUtils.parseHeader(header)
        assertThat(pair, Is.`is`(HttpRequestUtils.Pair("Content-Length", "59")))
    }
}