package app.web.controller.user

import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutController : AbstractController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val session = req.session
        session.invalidate()

        return jspView("redirect:/")
    }

}