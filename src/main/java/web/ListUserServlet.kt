package web

import db.DataBase
import model.User
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/user/list")
class ListUserServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val session = req.session
        val userSession = session.getAttribute("user")
        userSession.let {
            val user = it as User
            req.setAttribute("loginUser", user)
        }

        req.setAttribute("users", DataBase.findAll())
        val rd = req.getRequestDispatcher("/user/list.jsp")
        rd.forward(req, resp)
    }
}