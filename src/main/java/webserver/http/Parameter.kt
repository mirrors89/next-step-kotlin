package webserver.http

import util.HttpRequestUtils

data class Parameter(val parameter: Map<String, String> = hashMapOf()) {

    companion object {
        fun parse(queryString: String): Parameter {
            val query = HttpRequestUtils.parseQueryString(queryString)
            return Parameter(query)
        }
    }

    fun get(key: String): String? = parameter[key]
}
