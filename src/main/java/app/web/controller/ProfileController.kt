package app.web.controller

import core.mvc.Controller
import core.db.DataBase
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ProfileController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        val userId = req.getParameter("userId")

        val user = DataBase.findUserById(userId) ?: throw NullPointerException("사용자를 찾을 수 없습니다.")
        req.setAttribute("user", user)

        return "/user/profile.jsp"
    }

}
