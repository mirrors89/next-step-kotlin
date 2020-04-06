package web.controller

import model.User
import javax.servlet.http.HttpSession

class UserSessionUtils {
    companion object {
        private const val USER_SESSION_KEY = "user"

        fun getUserFromSession(session: HttpSession): User? {
            val user = session.getAttribute(USER_SESSION_KEY) ?: return null
            return user as User
        }

        fun isLogined(session: HttpSession): Boolean {
            return getUserFromSession(session) != null
        }

        fun isSameUser(session: HttpSession, user: User?): Boolean {
            if (!isLogined(session)) {
                return false
            }
            return getUserFromSession(session)?.let { user?.isSameUser(it) } ?: false
        }
    }
}
