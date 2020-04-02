package webserver.http

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream

class HttpResponseTest {


    companion object {
        private const val testDirectory = "./src/test/resources/"
    }

    @Test
    fun responseForward() {
        val httpResponse = HttpResponse(createOutputStream("Http_Forward.txt"))
        httpResponse.forward("/index.hml")
    }

    @Test
    fun responseRedirect() {
        val httpResponse = HttpResponse(createOutputStream("Http_Redirect.txt"))
        httpResponse.sendRedirect("/index.hml")
    }

    @Test
    fun responseCookies() {
        val httpResponse = HttpResponse(createOutputStream("Http_Cookie.txt"))
        httpResponse.addHeader("Set-Cookie", "logined=true")
        httpResponse.sendRedirect("/index.hml")
    }


    private fun createOutputStream(fileName: String): FileOutputStream {
        return FileOutputStream(File(testDirectory + fileName))
    }

}