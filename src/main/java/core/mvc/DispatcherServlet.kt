package core.mvc

import org.slf4j.LoggerFactory
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "dispatcher", urlPatterns = ["/"], loadOnStartup = 1)
class DispatcherServlet : HttpServlet() {

    companion object {
        private const val serialVersionUID = 1L
        private val logger = LoggerFactory.getLogger(DispatcherServlet::class.java)
    }

    private lateinit var rm: RequestMapping

    override fun init() {
        rm = RequestMapping()
        rm.initMapping()
    }

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {

        val requestUri = req.requestURI
        logger.debug("Method : {}, RequestURI : {}" , req.method, requestUri)
        val controller = rm.findController(requestUri)

        try {
            val modelAndView = controller!!.execute(req, resp)
            val view = modelAndView.view
            view.render(modelAndView.getModel(), req, resp)

        } catch (e: Exception) {
            throw ServletException(e.message)
        }
    }
}