package core.nmvc

import core.annotation.Controller
import core.annotation.RequestMapping
import core.annotation.RequestMethod
import core.mvc.ModelAndView
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class MyController {

    @RequestMapping("/users/findUserId")
    fun findUserId(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
        log.debug("findUserId")

        return null
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    fun save(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
        log.debug("save")

        return null
    }

    companion object {
        private val log = LoggerFactory.getLogger(MyController::class.java)
    }
}