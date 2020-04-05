package web

import db.DataBase
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/user/list")
class ListUserServlet: HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.setAttribute("users", DataBase.findAll())
        val rd = req.getRequestDispatcher("/user/list.jsp")
        rd.forward(req, resp)
    }
}