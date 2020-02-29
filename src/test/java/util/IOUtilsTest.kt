package util

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.StringReader

class IOUtilsTest {
    companion object {
        private val logger = LoggerFactory.getLogger(IOUtilsTest::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun readData() {
        val data = "abcd123"
        val sr = StringReader(data)
        val br = BufferedReader(sr)
        logger.debug("parse body : {}", IOUtils.readData(br, data.length))
    }
}