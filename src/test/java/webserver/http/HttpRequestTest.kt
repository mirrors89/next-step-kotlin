package webserver.http

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import kotlin.test.assertEquals

class HttpRequestTest {

    companion object {
        private const val testDirectory = "./src/test/resources/"
    }

    @Test
    fun request_GET() {
        val inputStream = FileInputStream(File(testDirectory + "Http_GET.txt"))
        val request = HttpRequest(inputStream)

        assertEquals(HttpMethod.GET, request.getMethod())
        assertEquals("/user/create", request.getPath())
        assertEquals("keep-alive", request.getHeader("Connection"))
        assertEquals("keeseung", request.getParameter("userId"))

    }
    @Test
    fun request_POST() {
        val inputStream = FileInputStream(File(testDirectory + "Http_POST.txt"))
        val request = HttpRequest(inputStream)

        assertEquals(HttpMethod.POST, request.getMethod())
        assertEquals("/user/create", request.getPath())
        assertEquals("keep-alive", request.getHeader("Connection"))
        assertEquals("keeseung", request.getParameter("userId"))

    }
}