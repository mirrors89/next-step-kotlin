package webserver

import db.DataBase
import model.User
import org.slf4j.LoggerFactory
import util.IOUtils
import webserver.http.HttpMethod
import webserver.http.Parameter
import webserver.http.RequestLine
import java.io.*
import java.net.Socket
import java.nio.file.Files

class RequestHandler(connectionSocket: Socket) : Thread() {
    private val log = LoggerFactory.getLogger(RequestHandler::class.java)
    private var connection: Socket = connectionSocket

    companion object {
        private const val WEBAPP_PATH = "./webapp"
        private const val CONTENT_LENGTH = "Content-Length"
    }

    override fun run() {
        val inputStream = connection.getInputStream()
        val outputStream = connection.getOutputStream()

        inputStream.use { i ->
            outputStream.use { o ->

                val bufferedReader = BufferedReader(InputStreamReader(i))
                var line = bufferedReader.readLine()
                val requestLine = RequestLine.parse(line)
                var contentLength = 0

                while(!line.equals("")) {
                    line = bufferedReader.readLine()

                    if(line.contains(CONTENT_LENGTH)) {
                        contentLength = getContentLength(line)
                    }
                }

                if(requestLine.method == HttpMethod.POST) {
                    if(requestLine.getPath() == "/user/create") {
                        val body = IOUtils.readData(bufferedReader, contentLength)
                        val params = Parameter.parse(body)
                        val user = User(params.getOrDefault("userId"), params.get("password"),
                                params.get("name"), params.get("email"))
                        DataBase.addUser(user)

                        log.info(user.toString())
                    }
                } else {
                    val body = Files.readAllBytes(File(WEBAPP_PATH + requestLine.getPath()).toPath())

                    val dos = DataOutputStream(o)
                    response200Header(dos, body.size)
                    responseBody(dos, body)
                }

            }
        }
    }

    private fun getContentLength(line: String): Int {
        val headerToken = line.split(":")
        return headerToken[1].trim().toInt()
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