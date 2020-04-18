package app.web

import org.apache.catalina.startup.Tomcat
import org.apache.catalina.webresources.DirResourceSet
import org.apache.catalina.webresources.StandardRoot
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

            val context = tomcat.addWebapp("/", File(webappDirLocation).absolutePath)
            val additionWebInfClasses = File("target/classes")
            val standardRoot = StandardRoot(context)
            standardRoot.addPreResources(DirResourceSet(standardRoot, "/WEB-INF/classes", additionWebInfClasses.absolutePath, "/"))
            context.resources = standardRoot

            println("configuring app with basedir: " + File("./$webappDirLocation").absolutePath)


            tomcat.start()
            tomcat.server.await()
        }
    }
}