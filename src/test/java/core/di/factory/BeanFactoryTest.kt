package core.di.factory

import core.annotation.Controller
import core.annotation.Repository
import core.annotation.Service
import core.di.factory.example.MyQnaService
import core.di.factory.example.QnaController
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.reflections.Reflections


class BeanFactoryTest {
    private lateinit var reflections: Reflections
    private lateinit var beanFactory: BeanFactory

    @BeforeEach
    fun setup() {
        reflections = Reflections("core.di.factory.example")
        val preInstanticateClazz = getTypesAnnotatedWith(Controller::class.java, Service::class.java, Repository::class.java)
        beanFactory = BeanFactory(preInstanticateClazz)
        beanFactory.initialize()
    }

    @Test
    @Throws(Exception::class)
    fun di() {
        val qnaController = beanFactory.getBean(QnaController::class.java)
        assertNotNull(qnaController)
        assertNotNull(qnaController.getQnaService())
        val qnaService: MyQnaService? = qnaController.getQnaService()
        assertNotNull(qnaService!!.getUserRepository())
        assertNotNull(qnaService.getQuestionRepository())
    }

    private fun getTypesAnnotatedWith(vararg annotations: Class<out Annotation?>): Set<Class<*>> {
        val beans: MutableSet<Class<*>> = mutableSetOf()
        for (annotation in annotations) {
            beans.addAll(reflections!!.getTypesAnnotatedWith(annotation))
        }
        return beans
    }
}