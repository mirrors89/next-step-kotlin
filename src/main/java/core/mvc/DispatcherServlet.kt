package core.mvc

import core.nmvc.*
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "dispatcher", urlPatterns = ["/"], loadOnStartup = 1)
class DispatcherServlet : HttpServlet() {


    private val mappings = arrayListOf<HandlerMapping>()
    private val handlerAdapters = arrayListOf<HandlerAdapter>()

    override fun init() {
        val lhm = LegacyHandlerMapping()
        lhm.initMapping()

        val ahm = AnnotationHandlerMapping("app.web.controller")
        ahm.initialize()

        mappings.add(lhm)
        mappings.add(ahm)

        handlerAdapters.add(ControllerHandlerAdapter())
        handlerAdapters.add(HandlerExecutionHandlerAdapter())
    }

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        val handler = getHandler(req) ?: throw IllegalArgumentException("존재하지 않는 URL 입니다.")

        try {
            val modelAndView = execute(handler, req, resp) ?: throw IllegalArgumentException("존재하지 않는 URL 입니다.")
            val view = modelAndView.view
            view.render(modelAndView.getModel(), req, resp)

        } catch (e: Exception) {
            throw ServletException(e.message)
        }
    }

    private fun getHandler(req: HttpServletRequest): Any? {
        for(handlerMapping in mappings)  {
            val handler = handlerMapping.getHandler(req)
            if(handler != null) {
                return handler
            }
        }

        return null
    }

    private fun execute(handler: Any, req: HttpServletRequest, res: HttpServletResponse): ModelAndView? {
        for(handlerAdapter in handlerAdapters) {
            if(handlerAdapter.supports(handler)) {
                return handlerAdapter.handle(req, res, handler)
            }
        }

        return null
    }

    companion object {
        private const val serialVersionUID = 1L
        private val logger = LoggerFactory.getLogger(DispatcherServlet::class.java)
    }
}