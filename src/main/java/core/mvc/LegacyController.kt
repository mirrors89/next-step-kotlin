package core.mvc

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface LegacyController {
    fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView
}