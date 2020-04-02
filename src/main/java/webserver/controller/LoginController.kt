package webserver.controller

import db.DataBase
import webserver.http.HttpRequest
import webserver.http.HttpResponse

class LoginController: Controller {

    companion object {
        private const val SET_COOKIE = "Set-Cookie"
    }

    override fun service(httpRequest: HttpRequest, httpResponse: HttpResponse) {

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
    }
}