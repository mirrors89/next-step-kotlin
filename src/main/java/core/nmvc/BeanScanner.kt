package core.nmvc

import core.annotation.Controller
import core.annotation.Repository
import core.annotation.Service
import org.slf4j.LoggerFactory
import org.reflections.Reflections
import java.lang.Exception

class BeanScanner(vararg basePackage: Any) {

    private val reflections = Reflections(basePackage)


    fun scan(): Set<Class<*>> {
        return getTypesAnnotatedWith(Controller::class.java, Service::class.java, Repository::class.java)
    }


    private fun getTypesAnnotatedWith(vararg annotations: Class<out Annotation?>): Set<Class<*>> {
        val beans: MutableSet<Class<*>> = mutableSetOf()
        for (annotation in annotations) {
            beans.addAll(reflections!!.getTypesAnnotatedWith(annotation))
        }
        return beans
    }

    companion object {
        private val log = LoggerFactory.getLogger(BeanScanner::class.java)
    }

}