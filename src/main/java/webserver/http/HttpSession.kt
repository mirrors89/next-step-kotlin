package webserver.http

import kotlin.collections.HashMap

class HttpSession(id: String) {
    companion object {
        private val values: HashMap<String, Any> = hashMapOf()
    }

    private val id = id

    fun setAttribute(name: String, value: Any): Any? = values.put(name, value)

    fun getAttribute(name: String): Any? = values[name]

    fun removeAttribute(name: String): Any? = values.remove(name)

    fun invalidate() = HttpSessions.remove(id)
}