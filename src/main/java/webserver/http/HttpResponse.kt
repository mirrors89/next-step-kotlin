package webserver.http

import org.slf4j.LoggerFactory
import webserver.RequestHandler
import java.io.*
import java.nio.file.Files

class HttpResponse(out: OutputStream) {

    companion object {
        private const val WEBAPP_PATH = "./webapp"
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONTENT_LENGTH = "Content-Length"
        private val log = LoggerFactory.getLogger(RequestHandler::class.java)
    }

    private val dos = DataOutputStream(out)
    private val headers = Headers()

    fun addHeader(key: String, value: String) {
        headers.put(key, value)
    }


    fun forward(url: String) {
        try {
            val body = Files.readAllBytes(File(WEBAPP_PATH + url).toPath())

            if(url.endsWith(".css")) {
                headers.put(CONTENT_TYPE, "text/css")
            } else if(url.endsWith(".js")) {
                headers.put(CONTENT_TYPE, "application/javascript")
            } else {
                headers.put(CONTENT_TYPE, "text/html;charset=utf-8")
            }

            headers.put(CONTENT_LENGTH, body.size.toString())
            response200Header()
            responseBody(body)

        } catch (e: IOException) {
            log.error(e.message)
        }
    }

    fun forwardBody(body: String) {
        val contents = body.toByteArray()
        headers.put(CONTENT_TYPE, "text/html;charset=utf-8")
        headers.put(CONTENT_LENGTH, contents.size.toString())

        response200Header()
        responseBody(contents)
    }

    fun sendRedirect(redirectUrl: String) {

        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n")
            dos.writeBytes("Location: $redirectUrl \r\n")
            dos.writeBytes("\r\n")
        } catch (e: IOException) {
            log.error(e.message)
        }
    }

    private fun response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n")
            processHeaders()
            dos.writeBytes("\r\n")
        } catch (e: IOException) {
            log.error(e.message)
        }
    }

    private fun responseBody(body: ByteArray) {
        try {
            dos.write(body, 0, body.size)
            dos.writeBytes("\r\n")
            dos.flush()
        } catch (e: IOException) {
            log.error(e.message)
        }
    }

    private fun processHeaders() {
        val keySet = headers.keySet()

        keySet.forEach { dos.writeBytes(it + ": " + headers.get(it) + " \r\n") }
    }
}