package util

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.jupiter.api.Test
import util.HttpRequestUtils.Companion.parseQueryString

class HttpRequestUtilsTest {
    @Test
    fun parseQueryString() {
        var queryString = "userId=javajigi"
        var parameters: Map<String?, String?> = parseQueryString(queryString)
        MatcherAssert.assertThat(parameters["userId"], Is.`is`("javajigi"))
        MatcherAssert.assertThat(parameters["password"], Is.`is`(CoreMatchers.nullValue()))
        queryString = "userId=javajigi&password=password2"
        parameters = parseQueryString(queryString)
        MatcherAssert.assertThat(parameters["userId"], Is.`is`("javajigi"))
        MatcherAssert.assertThat(parameters["password"], Is.`is`("password2"))
    }

    @Test
    fun parseQueryString_null() {
        var parameters: Map<String?, String?> = parseQueryString(null)
        MatcherAssert.assertThat(parameters.isEmpty(), Is.`is`(true))
        parameters = parseQueryString("")
        MatcherAssert.assertThat(parameters.isEmpty(), Is.`is`(true))
        parameters = parseQueryString(" ")
        MatcherAssert.assertThat(parameters.isEmpty(), Is.`is`(true))
    }

    @Test
    fun parseQueryString_invalid() {
        val queryString = "userId=javajigi&password"
        val parameters = parseQueryString(queryString)
        MatcherAssert.assertThat(parameters["userId"], Is.`is`("javajigi"))
        MatcherAssert.assertThat(parameters["password"], Is.`is`(CoreMatchers.nullValue()))
    }

    @Test
    fun parseCookies() {
        val cookies = "logined=true; JSessionId=1234"
        val parameters = HttpRequestUtils.parseCookies(cookies)
        MatcherAssert.assertThat(parameters["logined"], Is.`is`("true"))
        MatcherAssert.assertThat(parameters["JSessionId"], Is.`is`("1234"))
        MatcherAssert.assertThat(parameters["session"], Is.`is`(CoreMatchers.nullValue()))
    }

    @get:Throws(Exception::class)
    @get:Test
    val keyValue: Unit
        get() {
            val pair = HttpRequestUtils.getKeyValue("userId=javajigi", "=")
            MatcherAssert.assertThat(pair, Is.`is`(HttpRequestUtils.Pair("userId", "javajigi")))
        }

    @get:Throws(Exception::class)
    @get:Test
    val keyValue_invalid: Unit
        get() {
            val pair = HttpRequestUtils.getKeyValue("userId", "=")
            MatcherAssert.assertThat(pair, Is.`is`(CoreMatchers.nullValue()))
        }

    @Test
    @Throws(Exception::class)
    fun parseHeader() {
        val header = "Content-Length: 59"
        val pair = HttpRequestUtils.parseHeader(header)
        MatcherAssert.assertThat(pair, Is.`is`(HttpRequestUtils.Pair("Content-Length", "59")))
    }
}