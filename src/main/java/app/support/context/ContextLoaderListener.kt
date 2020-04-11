package app.support.context

import core.jdbc.ConnectionManager
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener

@WebListener
class ContextLoaderListener : ServletContextListener {
    override fun contextInitialized(sce: ServletContextEvent) {
        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource("jwp.sql"))
        DatabasePopulatorUtils.execute(populator, ConnectionManager.dataSource)
        logger.info("Completed Load ServletContext!")
    }

    override fun contextDestroyed(sce: ServletContextEvent) {}

    companion object {
        private val logger = LoggerFactory.getLogger(ContextLoaderListener::class.java)
    }
}