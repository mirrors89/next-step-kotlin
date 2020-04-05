package web

import db.DataBase
import model.User
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/update")
class UpdateUserFormServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val session = req.session
        val userSession = session.getAttribute("user")

        userSession?.let {
            val user : User = it as User
            req.setAttribute("user", DataBase.findUserById(user.userId))

            val rd = req.getRequestDispatcher("/user/update.jsp")
            rd.forward(req, resp)
        } ?: resp.sendRedirect("/user/login")
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))

        DataBase.addUser(user)
        resp.sendRedirect("/user/list")
    }
}