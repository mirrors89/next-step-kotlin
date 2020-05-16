package core.nmvc

import core.annotation.Controller
import org.slf4j.LoggerFactory
import org.reflections.Reflections
import java.lang.Exception

class ControllerScanner(vararg basePackage: Any) {

    private val reflections = Reflections(basePackage)

    fun getControllers(): Map<Class<*>, Any> {
        val preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller::class.java, true)

        return instantiateControllers(preInitiatedControllers)
    }

    private fun instantiateControllers(preInitiatedControllers: Set<Class<*>>): Map<Class<*>, Any> {
        val controllers = hashMapOf<Class<*>, Any>()
        try {

            for(clazz: Class<*> in preInitiatedControllers) {
                controllers[clazz] = clazz.getDeclaredConstructor().newInstance();
            }
        } catch (e: Exception) {
            when(e) {
                is InstantiationException, is IllegalAccessException -> {
                    log.error(e.message)
                }
            }
        }

        return controllers

    }

    companion object {
        private val log = LoggerFactory.getLogger(ControllerScanner::class.java)
    }

}