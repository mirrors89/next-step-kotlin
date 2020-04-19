package core.mvc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JsonView : View {
    override fun render(model: Map<String, Any?>, request: HttpServletRequest, response: HttpServletResponse) {
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

        response.contentType = "application/json;charset=UTF-8"
        val out = response.writer

        out.print(mapper.writeValueAsString(model))
    }

}