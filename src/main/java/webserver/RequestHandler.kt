package webserver

import db.DataBase
import model.User
import org.slf4j.LoggerFactory
import util.HttpRequestUtils
import webserver.http.HttpRequest
import webserver.http.HttpResponse
import java.net.Socket

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
                    when (path) {
                        "/user/create" -> {
                            createUser(httpRequest, httpResponse)
                        }
                        "/user/login" -> {
                            login(httpRequest, httpResponse)

                        }
                        "/user/list" -> {
                            listUser(httpRequest, httpResponse)
                        }
                        else -> return
                    }
                } else {
                    httpResponse.forward(path)
                }

            }
        }
    }

    private fun createUser(httpRequest: HttpRequest, httpResponse: HttpResponse) {
        val user = User(httpRequest.getParameter("userId")!!,
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"))

        DataBase.addUser(user)
        httpResponse.sendRedirect("/index.html")
    }

    private fun login(httpRequest: HttpRequest, httpResponse: HttpResponse) {
        val user = DataBase.findUserById(httpRequest.getParameter("userId"))
        if (user == null) {
            httpResponse.sendRedirect("/user/login_failed.html")
            return
        }

        if (user.isLogin(httpRequest.getParameter("password"))) {
            httpResponse.addHeader(SET_COOKIE, "logined=true")
            httpResponse.sendRedirect("/index.html")
            return
        }

        httpResponse.sendRedirect("/user/login_failed.html")
        return
    }

    private fun listUser(httpRequest: HttpRequest, httpResponse: HttpResponse): Boolean {
        if (!isLogin(httpRequest.getHeader(COOKIE))) {
            httpResponse.sendRedirect("/user/login.html")
            return true
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
        return false
    }

    private fun isLogin(line: String?): Boolean {
        var headerTokens = line?.split(SPLIT_SEPARATOR)
        val cookies = HttpRequestUtils.parseCookies(headerTokens!![1].trim())

        val value = cookies.get(LOGINED) ?: return false

        return value.toBoolean()

    }
}