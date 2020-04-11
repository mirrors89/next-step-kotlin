package core.jdbc

import org.apache.commons.dbcp2.BasicDataSource
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource

object ConnectionManager {
    private const val DB_DRIVER = "org.h2.Driver"
    private const val DB_URL = "jdbc:h2:~/jwp-basic;AUTO_SERVER=TRUE"
    private const val DB_USERNAME = "sa"
    private const val DB_PW = ""

    fun getDataSource(): DataSource {
        val ds = BasicDataSource()
        ds.driverClassName = DB_DRIVER
        ds.url = DB_URL
        ds.username = DB_USERNAME
        ds.password = DB_PW
        return ds
    }

    fun getConnection(): Connection = getDataSource().connection
}