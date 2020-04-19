package app.web.controller.user

import app.dao.UserDao
import core.db.DataBase
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ProfileController : AbstractController() {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val userId = req.getParameter("userId")

        val userDao = UserDao()
        val user = userDao.findByUserId(userId) ?: throw NullPointerException("사용자를 찾을 수 없습니다.")

        return jspView("/user/profile.jsp")
                .addObject("user", user)
    }

}
