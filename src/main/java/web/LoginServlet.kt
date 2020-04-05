package web

import db.DataBase
import model.User
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/user/login")
class LoginServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val rd = req.getRequestDispatcher("/user/login.jsp")
        rd.forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val user = DataBase.findUserById(req.getParameter("userId"))
        if (user == null) {
            loginFail(req, resp)
            return
        }

        if (user.isLogin(req.getParameter("password"))) {
            loginSuccess(req, resp, user)
            return
        }

        loginFail(req, resp)
    }

    private fun loginSuccess(req: HttpServletRequest, resp: HttpServletResponse, user: User) {
        val session = req.session
        session.setAttribute("user", user)
        resp.sendRedirect("/")
    }

    private fun loginFail(req: HttpServletRequest, resp: HttpServletResponse) {
        val rd = req.getRequestDispatcher("/user/login_failed.jsp")
        rd.forward(req, resp)
    }
}