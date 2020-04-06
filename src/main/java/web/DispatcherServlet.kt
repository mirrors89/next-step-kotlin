package web

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
        private const val DEFAULT_REDIRECT_PREFIX = "redirect:"
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
            val viewName = controller!!.execute(req, resp)
            move(viewName, req, resp)

        } catch (e: Exception) {
            throw ServletException(e.message)
        }
    }

    private fun move(viewName: String, req: HttpServletRequest, resp: HttpServletResponse) {
        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length))
            return
        }

        val rd = req.getRequestDispatcher(viewName)
        rd.forward(req, resp)
    }
}