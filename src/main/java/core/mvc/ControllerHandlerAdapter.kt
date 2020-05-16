package core.mvc

import core.nmvc.HandlerAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ControllerHandlerAdapter : HandlerAdapter {
    override fun supports(handler: Any): Boolean {
        return handler is LegacyController
    }

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): ModelAndView {
        val legacyController = handler as LegacyController
        return legacyController.execute(request, response)
    }
}