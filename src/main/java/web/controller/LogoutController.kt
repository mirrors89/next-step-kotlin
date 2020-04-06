package web.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        val session = req.session
        session.invalidate()

        return "redirect:/"
    }

}