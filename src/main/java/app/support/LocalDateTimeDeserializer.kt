package app.support

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer protected constructor() : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime {
        return LocalDateTime.parse(parser.readValueAs(String::class.java),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}