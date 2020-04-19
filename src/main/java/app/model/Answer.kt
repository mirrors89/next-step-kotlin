package app.model

import app.support.LocalDateTimeDeserializer
import app.support.LocalDateTimeSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Answer(var answerId: Long? = null,
                  var questionId: Long,
                  var writer: String,
                  var contents: String,
                  @JsonDeserialize(using = LocalDateTimeDeserializer::class)
                  @JsonSerialize(using = LocalDateTimeSerializer::class)
                  var createdDate: LocalDateTime) {

    constructor(questionId: Long, writer: String, contents: String) :
            this(0, questionId, writer, contents, LocalDateTime.now())

    fun getCreatedDate(): String = createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

}