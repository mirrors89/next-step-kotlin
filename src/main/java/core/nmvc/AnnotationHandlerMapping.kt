package core.nmvc

import core.annotation.RequestMapping
import org.reflections.ReflectionUtils
import org.slf4j.LoggerFactory
import java.lang.reflect.Method

class AnnotationHandlerMapping(private val basePackage: Array<Any>,
                               private val handlerExecutions: HashMap<HandleKey, HandlerExecution> = hashMapOf()) {

    fun initialize() {
        val controllerScanner  = ControllerScanner(basePackage)

        val controllers = controllerScanner.getControllers()
        val methods: Set<Method> = getRequestMappingMethods(controllers.keys)

        for(method in methods) {
            val rm = method.getAnnotation(RequestMapping::class.java)
            log.debug("register handlerExecution : url is {}, method is {}", rm.value, method)

            handlerExecutions[createHandlerKey(rm)] =  HandlerExecution(controllers[method.declaringClass], method)
        }
    }

    private fun createHandlerKey(rm: RequestMapping): HandleKey {
        return HandleKey(rm.value, rm.method)
    }

    private fun getRequestMappingMethods(keys: Set<Class<*>>): Set<Method> {

        val requestMappingMethods = hashSetOf<Method>()
        for(clazz in keys) {
            requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping::class.java)))
        }

        return requestMappingMethods
    }


    companion object {
        private val log = LoggerFactory.getLogger(AnnotationHandlerMapping::class.java)
    }
}