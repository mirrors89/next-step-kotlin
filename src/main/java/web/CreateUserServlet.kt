package web

import db.DataBase
import model.User
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/create")
class CreateUserServlet: HttpServlet() {

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = User(req.getParameter("userId")!!,
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))

        DataBase.addUser(user)
        resp.sendRedirect("/user/list")
    }
}