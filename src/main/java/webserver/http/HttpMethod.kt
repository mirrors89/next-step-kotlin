package webserver.http

enum class HttpMethod {
    GET, POST;

    companion object {
        fun find(method: String): HttpMethod {
            return valueOf(method.toUpperCase())
        }
    }
}
