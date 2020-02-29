package webserver

import org.slf4j.LoggerFactory
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class RequestHandler(connectionSocket: Socket) : Thread() {
    private val log = LoggerFactory.getLogger(RequestHandler::class.java)

    private var connection: Socket = connectionSocket

    override fun run() {

        val inputStream = connection.getInputStream()
        val outputStream = connection.getOutputStream()

        inputStream.use { i ->
            outputStream.use { o ->
                // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
                val dos = DataOutputStream(o)
                val body = "Hello World".toByteArray()
                response200Header(dos, body.size)
                responseBody(dos, body)
            }
        }
    }

    private fun response200Header(dos: DataOutputStream, lengthOfBodyContent: Int) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n")
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n")
            dos.writeBytes("Content-Length: $lengthOfBodyContent\r\n")
            dos.writeBytes("\r\n")
        } catch (e: IOException) {
            log.error(e.message)
        }
    }

    private fun responseBody(dos: DataOutputStream, body: ByteArray) {
        try {
            dos.write(body, 0, body.size)
            dos.flush()
        } catch (e: IOException) {
            log.error(e.message)
        }
    }
}