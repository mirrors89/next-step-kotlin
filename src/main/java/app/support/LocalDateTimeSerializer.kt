package app.support

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    @Throws(IOException::class)
    override fun serialize(value: LocalDateTime, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
    }
}