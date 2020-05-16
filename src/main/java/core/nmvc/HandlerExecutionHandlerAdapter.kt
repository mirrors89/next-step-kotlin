package core.nmvc

import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HandlerExecutionHandlerAdapter : HandlerAdapter {
    override fun supports(handler: Any): Boolean {
        return handler is HandlerExecution
    }

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): ModelAndView {
        val handlerExecution = handler as HandlerExecution

        return handlerExecution.handle(request, response)
    }
}