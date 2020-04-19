package app.web.controller.user

import app.dao.UserDao
import app.web.controller.UserSessionUtils
import core.db.DataBase
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UpdateFormUserController : AbstractController() {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {

        val userDao = UserDao()
        val userId = req.getParameter("userId")
        val user = userDao.findByUserId(userId)

        check(UserSessionUtils.isSameUser(req.session, user)) { "다른 사용자의 정보를 수정할 수 없습니다." }

        return jspView("/user/update.jsp")
                .addObject("user", user)
    }

}
