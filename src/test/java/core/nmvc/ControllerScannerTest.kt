package core.nmvc

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

internal class ControllerScannerTest {

    private lateinit var cf: ControllerScanner

    @BeforeEach
    fun setup() {
        cf = ControllerScanner("core.nmvc")
    }

    @Test
    @Throws(Exception::class)
    fun getControllers() {
        val controllers: Map<Class<*>, Any> = cf.getControllers()
        for(controller: Class<*> in controllers.keys) {
            log.debug("controller : {}", controller)
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(ControllerScannerTest::class.java)
    }
}