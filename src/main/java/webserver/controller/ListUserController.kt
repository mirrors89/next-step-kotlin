package webserver.controller

import db.DataBase
import util.HttpRequestUtils
import webserver.http.HttpRequest
import webserver.http.HttpResponse
import webserver.http.HttpSession

class ListUserController : AbstractController() {

    companion object {
        private const val COOKIE = "Cookie"
        private const val LOGINED = "logined"
        private const val SPLIT_SEPARATOR = ":"
    }

    override fun doGet(httpRequest: HttpRequest, httpResponse: HttpResponse) {
        TODO("Not yet implemented")
    }

    override fun doPost(httpRequest: HttpRequest, httpResponse: HttpResponse) {
        if (!isLogin(httpRequest.getSession())) {
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

        httpResponse.forwardBody(sb.toString())    }


    private fun isLogin(httpSession: HttpSession): Boolean {
        httpSession.getAttribute("user") ?: return false
        return true
    }
}