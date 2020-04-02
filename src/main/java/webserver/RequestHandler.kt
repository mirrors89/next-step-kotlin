package webserver

import db.DataBase
import model.User
import org.slf4j.LoggerFactory
import util.HttpRequestUtils
import util.IOUtils
import webserver.http.HttpMethod
import webserver.http.Parameters
import webserver.http.HttpRequest
import webserver.http.HttpResponse
import java.io.*
import java.net.Socket
import java.nio.file.Files

class RequestHandler(connectionSocket: Socket) : Thread() {
    private val log = LoggerFactory.getLogger(RequestHandler::class.java)
    private var connection: Socket = connectionSocket

    companion object {
        private const val COOKIE = "Cookie"
        private const val SET_COOKIE = "Set-Cookie"
        private const val LOGINED = "logined"
        private const val SPLIT_SEPARATOR = ":"
    }

    override fun run() {
        val inputStream = connection.getInputStream()
        val outputStream = connection.getOutputStream()

        inputStream.use { i ->
            outputStream.use { o ->

                val httpRequest = HttpRequest(i)
                val httpResponse = HttpResponse(o)
                val path = httpRequest.getPath()

                if(httpRequest.getMethod().isPost()) {
                    if(path == "/user/create") {
                        val user = User(httpRequest.getParameter("userId")!!,
                                httpRequest.getParameter("password"),
                                httpRequest.getParameter("name"),
                                httpRequest.getParameter("email"))

                        DataBase.addUser(user)
                        httpResponse.sendRedirect("/index.html")

                    } else if(path == "/user/login") {
                        val user = DataBase.findUserById(httpRequest.getParameter("userId"))
                        if(user == null) {
                            httpResponse.sendRedirect("/user/login_failed.html")
                            return
                        }

                        if(user.isLogin(httpRequest.getParameter("password"))) {
                            httpResponse.addHeader(SET_COOKIE, "logined=true")
                            httpResponse.sendRedirect("/index.html")
                            return
                        }

                        httpResponse.sendRedirect("/user/login_failed.html")

                    } else if(path == "/user/list") {
                        if(!isLogin(httpRequest.getHeader(COOKIE))) {
                            httpResponse.sendRedirect("/user/login.html")
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


                        httpResponse.forwardBody(sb.toString())
                    }
                } else {
                    httpResponse.forward(path)
                }

            }
        }
    }

    private fun isLogin(line: String?): Boolean {
        var headerTokens = line?.split(SPLIT_SEPARATOR)
        val cookies = HttpRequestUtils.parseCookies(headerTokens!![1].trim())

        val value = cookies.get(LOGINED) ?: return false

        return value.toBoolean()

    }
}