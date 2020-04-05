package webserver.http

import util.HttpRequestUtils

class HttpCookie(cookieValue: String?) {
    private val cookies = HttpRequestUtils.parseCookies(cookieValue)


    fun getCookie(name: String): String? {
        return cookies[name]
    }
}