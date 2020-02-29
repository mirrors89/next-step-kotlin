package webserver

import org.slf4j.LoggerFactory
import java.net.ServerSocket
import java.net.Socket

private val logger = LoggerFactory.getLogger("WebServer")
private val DEFAULT_PORT = 8080


fun main(args: Array<String>) {
    val port = if(args.isEmpty()) {
        DEFAULT_PORT
    } else {
        args.get(0).toInt()
    }

    // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
    val listenSocket = ServerSocket(port)
    listenSocket.use {
        logger.info("Web Application Server started {} port.", port)

        // 클라이언트가 연결될때까지 대기한다.
        var connection: Socket?
        do {
            connection = listenSocket.accept()
            val requestHandler = RequestHandler(connection)
            requestHandler.start()

        } while (connection != null)
    }
}