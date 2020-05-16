package app.web.controller.user

import app.dao.UserDao
import app.model.User
import app.web.controller.UserSessionUtils
import core.annotation.Controller
import core.annotation.RequestMapping
import core.annotation.RequestMethod
import core.db.DataBase
import core.mvc.ModelAndView
import core.nmvc.AbstractNewController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class UserController : AbstractNewController() {

    private val userDao = UserDao.getInstance()

    @RequestMapping("/users")
    fun list(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        if(!UserSessionUtils.isLogined(request.session)) {
            return jspView("redirect:/users/loginForm")
        }

        val mav = jspView("/user/list.jsp")
        mav.addObject("users", userDao.findAll())
        return mav
    }

    @RequestMapping("/user/profile")
    fun profile(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        val userId = request.getParameter("userId")
        val user = userDao.findByUserId(userId) ?: throw NullPointerException("사용자를 찾을 수 없습니다.")

        return jspView("/user/profile.jsp")
                .addObject("user", user)
    }

    @RequestMapping("/users/form")
    fun form(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        return jspView("/user/form.jsp")
    }


    @RequestMapping("/user/create", method = RequestMethod.POST)
    fun create(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        val user = User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"))


        userDao.insert(user)
        val session = request.session
        session.setAttribute("user", user)
        return jspView("redirect:/")
    }

    @RequestMapping("/user/updateForm")
    fun updateForm(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        return jspView("/user/updateForm.jsp")
    }


    @RequestMapping("/user/update")
    fun update(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        val user = userDao.findByUserId(request.getParameter("userId"))
        check(UserSessionUtils.isSameUser(request.session, user)) { "다른 사용자의 정보를 수정할 수 없습니다." }

        val updateUser = User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"))

        userDao.update(updateUser)
        return jspView("redirect:/users")
    }

}