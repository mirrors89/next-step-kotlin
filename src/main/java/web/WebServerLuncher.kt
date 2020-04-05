package web

import org.apache.catalina.startup.Tomcat
import java.io.File

class WebServerLuncher {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val webappDirLocation = "webapp/"
            val tomcat = Tomcat()
            tomcat.setPort(8080)

            val connector = tomcat.connector
            connector.uriEncoding = "UTF-8"
            tomcat.addWebapp("/", File(webappDirLocation).absolutePath)
            println("configuring app with basedir: " + File("./$webappDirLocation").absolutePath)
            tomcat.start()
            tomcat.server.await()
        }
    }
}