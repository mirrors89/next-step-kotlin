package core.nmvc

import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface HandlerAdapter {
    fun supports(handler: Any): Boolean

    fun handle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): ModelAndView
}