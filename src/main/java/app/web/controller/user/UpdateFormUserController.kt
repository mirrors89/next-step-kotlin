package app.web.controller.user

import app.dao.UserDao
import app.web.controller.UserSessionUtils
import core.mvc.Controller
import core.db.DataBase
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UpdateFormUserController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {

        val userDao = UserDao()
        val userId = req.getParameter("userId")
        val user = userDao.findByUserId(userId)

        check(UserSessionUtils.isSameUser(req.session, user)) { "다른 사용자의 정보를 수정할 수 없습니다." }
        req.setAttribute("user", user)

        return "/user/update.jsp"
    }

}
