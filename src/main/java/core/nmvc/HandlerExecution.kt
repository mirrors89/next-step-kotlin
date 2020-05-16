package core.nmvc

import core.mvc.ModelAndView
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HandlerExecution(private val declaredObject: Any?, private val method: Method) {

    fun handle(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        try {
            return method.invoke(declaredObject, request, response) as ModelAndView

        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException,
                is IllegalAccessException,
                is InvocationTargetException -> {

                    log.error("{} method invoke fail. error message : {}", method, e.message)
                    throw RuntimeException(e)
                }
            }
        }
        throw RuntimeException()
    }

    companion object {
        private val log = LoggerFactory.getLogger(HandlerExecution::class.java)
    }

}