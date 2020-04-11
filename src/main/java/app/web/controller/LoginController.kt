package app.web.controller

import core.mvc.Controller
import core.db.DataBase
import app.model.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {

        val user = DataBase.findUserById(req.getParameter("userId")) ?: return "/user/login_failed.jsp"

        if (user.isLogin(req.getParameter("password"))) {
            loginSuccess(req, resp, user)
            return "redirect:/users"

        }

        return "/user/login_failed.jsp"
    }



    private fun loginSuccess(req: HttpServletRequest, resp: HttpServletResponse, user: User)  {
        val session = req.session
        session.setAttribute("user", user)
    }

}