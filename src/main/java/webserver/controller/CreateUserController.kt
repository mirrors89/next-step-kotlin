package webserver.controller

import db.DataBase
import model.User
import webserver.http.HttpRequest
import webserver.http.HttpResponse

class CreateUserController: Controller {
    override fun service(httpRequest: HttpRequest, httpResponse: HttpResponse) {
        val user = User(httpRequest.getParameter("userId")!!,
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"))

        DataBase.addUser(user)
        httpResponse.sendRedirect("/index.html")
    }
}