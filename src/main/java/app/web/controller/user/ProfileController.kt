package app.web.controller.user

import app.dao.UserDao
import core.mvc.Controller
import core.db.DataBase
import core.mvc.JspView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ProfileController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val userId = req.getParameter("userId")

        val userDao = UserDao()
        val user = userDao.findByUserId(userId) ?: throw NullPointerException("사용자를 찾을 수 없습니다.")
        req.setAttribute("user", user)

        return JspView("/user/profile.jsp")
    }

}
