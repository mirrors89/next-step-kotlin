package webserver.controller

import db.DataBase
import util.HttpRequestUtils
import webserver.RequestHandler
import webserver.http.HttpRequest
import webserver.http.HttpResponse

class ListUserController : Controller {

    companion object {
        private const val COOKIE = "Cookie"
        private const val LOGINED = "logined"
        private const val SPLIT_SEPARATOR = ":"
    }

    override fun service(httpRequest: HttpRequest, httpResponse: HttpResponse) {

        if (!isLogin(httpRequest.getHeader(COOKIE))) {
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


    private fun isLogin(line: String?): Boolean {
        var headerTokens = line?.split(SPLIT_SEPARATOR)
        val cookies = HttpRequestUtils.parseCookies(headerTokens!![1].trim())

        val value = cookies.get(LOGINED) ?: return false

        return value.toBoolean()
    }
}