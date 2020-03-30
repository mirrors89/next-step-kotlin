package webserver

import db.DataBase
import model.User
import org.slf4j.LoggerFactory
import util.HttpRequestUtils
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
        private const val COOKIE = "Cookie"
        private const val LOGINED = "logined"
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
                var logined = false

                while(!line.equals("")) {
                    line = bufferedReader.readLine()

                    if(line.contains(COOKIE)) {
                        logined = isLogin(line)
                    }

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

                        response302LoginSuccessHeader(DataOutputStream(o))
                    } else if(requestLine.getPath() == "/user/login") {
                        val body = IOUtils.readData(bufferedReader, contentLength)
                        val params = Parameter.parse(body)

                        val user = DataBase.findUserById(params.get("userId"))
                        if(user == null) {
                            responseResource(o, "/user/login_failed.html")
                        }

                        if(user?.password == params.get("password")) {
                            response302LoginSuccessHeader(DataOutputStream(o))
                        } else {
                            responseResource(o, "/user/login_failed.html")
                        }
                    } else if(requestLine.getPath() == "/user/list") {
                        if(!logined) {
                            responseResource(o, "/user/login.html")
                            return
                        }

                        val users = DataBase.findAll()
                        val sb = StringBuffer()
                        sb.append("<table border='1'>")
                        users.stream().forEach {
                            sb.append("<tr>")
                            sb.append("<td>" + it.userId + "</td>")
                            sb.append("<td>" + it.name + "</td>")
                            sb.append("<td>" + it.email + "</td>")
                            sb.append("</tr>")
                        }
                        sb.append("</table>")

                        val body = sb.toString().toByteArray()
                        val dos = DataOutputStream(o)
                        response200Header(dos, body.size)
                        responseBody(dos, body)
                    }
                } else {
                    responseResource(o, requestLine.getPath())
                }

            }
        }
    }

    private fun isLogin(line: String?): Boolean {
        var headerTokens = line?.split(":")
        val cookies = HttpRequestUtils.parseCookies(headerTokens!![1].trim())

        val value = cookies.get(LOGINED) ?: return false

        return value.toBoolean()

    }

    private fun getContentLength(line: String): Int {
        val headerToken = line.split(":")
        return headerToken[1].trim().toInt()
    }

    private fun responseResource(o: OutputStream, url: String) {
        val dos = DataOutputStream(o)
        val body = Files.readAllBytes(File(WEBAPP_PATH + url).toPath())

        response200Header(dos, body.size)
        responseBody(dos, body)
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

    private fun response302LoginSuccessHeader(dos: DataOutputStream) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n")
            dos.writeBytes("Set-Cookie: logined=true \r\n")
            dos.writeBytes("Location: /index.html \r\n")
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