package app.web.controller.user

import app.dao.UserDao
import core.db.DataBase
import app.model.User
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginController : AbstractController() {

    private val userDao = UserDao()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val user = userDao.findByUserId(req.getParameter("userId")) ?: return jspView("/user/login_failed.jsp")

        if (user.isLogin(req.getParameter("password"))) {
            loginSuccess(req, resp, user)
            return jspView("redirect:/users")

        }

        return jspView("/user/login_failed.jsp")
    }

    private fun loginSuccess(req: HttpServletRequest, resp: HttpServletResponse, user: User)  {
        val session = req.session
        session.setAttribute("user", user)
    }

}