package app.web.controller.qna

import app.web.controller.UserSessionUtils
import core.mvc.AbstractController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FormController : AbstractController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val session = req.session
        check(UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/user/loginForm")
        }

        val mav = jspView("form.jsp")
        mav.addObject("user", UserSessionUtils.getUserFromSession(session))

        return mav
    }

}
