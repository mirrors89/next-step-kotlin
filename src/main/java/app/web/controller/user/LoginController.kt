package app.web.controller.user

import app.dao.UserDao
import app.model.User
import core.annotation.Controller
import core.annotation.RequestMapping
import core.mvc.ModelAndView
import core.nmvc.AbstractNewController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class LoginController: AbstractNewController() {

    private val userDao = UserDao.getInstance()

    @RequestMapping("/user/loginForm")
    fun loginForm(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        return jspView("/user/login.jsp")
    }

    @RequestMapping("/user/login")
    fun login(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val user = userDao.findByUserId(req.getParameter("userId")) ?: return jspView("/user/login_failed.jsp")

        if (user.isLogin(req.getParameter("password"))) {
            loginSuccess(req, resp, user)
            return jspView("redirect:/users")

        }

        return jspView("/user/login_failed.jsp")
    }

    @RequestMapping("/user/logout")
    fun logout(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val session = req.session
        session.invalidate()

        return jspView("redirect:/")
    }

    private fun loginSuccess(req: HttpServletRequest, resp: HttpServletResponse, user: User)  {
        val session = req.session
        session.setAttribute("user", user)
    }
}