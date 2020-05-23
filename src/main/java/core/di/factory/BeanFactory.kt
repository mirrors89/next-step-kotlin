package core.di.factory

import core.annotation.Controller
import core.nmvc.ControllerScanner
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.reflect.Constructor

class BeanFactory(private val preInstanticateBeans: Set<Class<*>>) {

    private val beans: HashMap<Class<*>, Any> = hashMapOf()


    fun <T> getBean(requiredType: Class<T>): T {
        return beans[requiredType] as T
    }

    fun initialize() {
        for(clazz: Class<*> in preInstanticateBeans) {
            if(beans[clazz] == null) {
                instantiateClass(clazz)
            }
        }
    }

    fun instantiateClass(clazz: Class<*>): Any {
        var bean: Any? = beans[clazz]
        if(bean != null) {
            return bean
        }

        val injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz)
        if(injectedConstructor == null) {
            bean = BeanUtils.instantiateClass(clazz)
            beans[clazz] = bean
            return bean
        }

        logger.debug("Constructor : {}", injectedConstructor)
        bean = instantiateConstructor(injectedConstructor)
        beans[clazz] = bean
        return bean
    }

    private fun instantiateConstructor(constructor: Constructor<*>): Any {
        val parameterTypes = constructor.parameterTypes
        val args = arrayListOf<Any>()
        for(clazz: Class<*> in parameterTypes) {
            val concreateClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans)
            if(!preInstanticateBeans.contains(concreateClazz)) {
                throw IllegalStateException("${clazz}는 Bean이 아니다.")
            }

            var bean = beans[concreateClazz]
            if(bean == null) {
                bean = instantiateClass(concreateClazz)
            }
            args.add(bean)
        }

        return BeanUtils.instantiateClass(constructor, *args.toTypedArray())

    }

    fun getControllers(): Map<Class<*>, Any> {
        val controllers = hashMapOf<Class<*>, Any>()
        for(clazz: Class<*> in preInstanticateBeans) {
            val annotation = clazz.getAnnotation(Controller::class.java)
            if(annotation != null) {
                controllers[clazz] = clazz.getDeclaredConstructor().newInstance()
            }
        }
        return controllers
    }

    companion object {
        private val logger = LoggerFactory.getLogger(BeanFactory::class.java)
    }

}