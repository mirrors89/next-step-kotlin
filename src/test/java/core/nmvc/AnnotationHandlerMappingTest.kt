package core.nmvc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

internal class AnnotationHandlerMappingTest {

    private lateinit var cf: ControllerScanner
    private lateinit var handlerMapping: AnnotationHandlerMapping
    private lateinit var response: MockHttpServletResponse


    @BeforeEach
    fun setup() {
        handlerMapping = AnnotationHandlerMapping("core.nmvc")
        handlerMapping.initialize()

        response = MockHttpServletResponse()
    }

    @Test
    @Throws(Exception::class)
    fun list() {
        val request = MockHttpServletRequest("GET", "/users")

        val execution = handlerMapping.getHandler(request)
        val mav = execution.handle(request, response)
        mav.view.render(mav.getModel(), request, response)
        assertEquals("/users/list.jsp", response.forwardedUrl)
    }

    @Test
    @Throws(Exception::class)
    fun show() {
        val request = MockHttpServletRequest("GET", "/users/show")

        val execution = handlerMapping.getHandler(request)
        val mav = execution.handle(request, response)
        mav.view.render(mav.getModel(), request, response)
        assertEquals("/users/show.jsp", response.forwardedUrl)
    }

    @Test
    @Throws(Exception::class)
    fun create() {
        val request = MockHttpServletRequest("POST", "/users")

        val execution = handlerMapping.getHandler(request)
        val mav = execution.handle(request, response)
        mav.view.render(mav.getModel(), request, response)
        assertEquals("/users", response.redirectedUrl);
    }



    companion object {
        val log = LoggerFactory.getLogger(ControllerScannerTest::class.java)
    }
}