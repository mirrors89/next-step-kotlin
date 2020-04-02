package webserver.http

enum class HttpMethod {
    GET, POST;

    companion object {
        fun find(method: String): HttpMethod {
            return valueOf(method.toUpperCase())
        }

    }

    fun isPost(): Boolean {
        return this == POST
    }
}
