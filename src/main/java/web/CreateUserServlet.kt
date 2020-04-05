package web

import db.DataBase
import model.User
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/create")
class CreateUserServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val rd = req.getRequestDispatcher("/user/form.jsp")
        rd.forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))
        DataBase.addUser(user)

        val session = req.session
        session.setAttribute("user", user)
        resp.sendRedirect("/user/list")
    }
}