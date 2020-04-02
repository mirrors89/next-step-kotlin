package webserver.http

import util.HttpRequestUtils

class Parameters(val parameter: Map<String, String> = hashMapOf()) {

    companion object {
        private const val DEFAULT_STRING = ""


        fun parse(queryString: String): Parameters {
            val query = HttpRequestUtils.parseQueryString(queryString)
            return Parameters(query)
        }
    }

    fun get(key: String): String? = parameter[key]

    fun getOrDefault(key: String): String = parameter.getOrDefault(key, DEFAULT_STRING)

}
