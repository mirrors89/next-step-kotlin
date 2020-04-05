package webserver.http

import kotlin.collections.HashMap

class HttpSessions {

    companion object {
        private val sessions: HashMap<String, HttpSession> = hashMapOf()

        fun getSession(id: String): HttpSession {
            var session = sessions[id]

            return if(session == null) {
                session = HttpSession(id)
                sessions[id] = session
                return session
            } else {
                return session;
            }
        }

        fun remove(id: String) {
            sessions.remove(id)
        }
    }


}