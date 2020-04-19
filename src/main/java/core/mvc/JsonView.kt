package core.mvc

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JsonView : View {
    override fun render(request: HttpServletRequest, response: HttpServletResponse) {
        val mapper = ObjectMapper()
        response.contentType = "application/json;charset=UTF-8"
        val out = response.writer

        out.print(mapper.writeValueAsString(createModel(request)))
    }

    private fun createModel(request: HttpServletRequest): Map<String, Any> {
        val attributeNames = request.attributeNames
        val model = HashMap<String, Any>()
        while (attributeNames.hasMoreElements()) {
            val attributeName = attributeNames.nextElement()
            model[attributeName] = request.getAttribute(attributeName)
        }

        return model
    }

}