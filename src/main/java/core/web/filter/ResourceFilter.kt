package core.web.filter

import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest


@WebFilter("/*")
class ResourceFilter : Filter {
    private val logger = LoggerFactory.getLogger(ResourceFilter::class.java)
    private val resourcePrefixs: MutableList<String> = ArrayList()
    init {
        resourcePrefixs.add("/css")
        resourcePrefixs.add("/js")
        resourcePrefixs.add("/fonts")
        resourcePrefixs.add("/images")
        resourcePrefixs.add("/favicon.ico")
    }

    private var defaultRequestDispatcher: RequestDispatcher? = null

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        defaultRequestDispatcher = filterConfig.servletContext.getNamedDispatcher("default")
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse?, chain: FilterChain) {
        val req = request as HttpServletRequest
        val path = req.requestURI.substring(req.contextPath.length)

        if (isResourceUrl(path)) {
            logger.debug("path : {}", path)
            defaultRequestDispatcher!!.forward(request, response)
            return
        }

        chain.doFilter(request, response)
    }

    private fun isResourceUrl(url: String): Boolean {
        for (prefix in resourcePrefixs) {
            if (url.startsWith(prefix)) {
                return true
            }
        }
        return false
    }
}