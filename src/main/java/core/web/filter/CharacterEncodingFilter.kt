package core.web.filter

import java.io.IOException
import javax.servlet.*
import javax.servlet.annotation.WebFilter

@WebFilter("/*")
class CharacterEncodingFilter : Filter {
    companion object {
        private const val DEFAULT_ENCODING = "UTF-8"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request.characterEncoding = DEFAULT_ENCODING
        response.characterEncoding = DEFAULT_ENCODING

        chain.doFilter(request, response)
    }
}