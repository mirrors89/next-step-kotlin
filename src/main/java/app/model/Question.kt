package app.model

import app.support.LocalDateTimeDeserializer
import app.support.LocalDateTimeSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Question(var questionId: Long,
               var writer: String,
               var title: String,
               var contents: String,
               var countOfAnswer: Int = 0,
                @JsonDeserialize(using = LocalDateTimeDeserializer::class)
                @JsonSerialize(using = LocalDateTimeSerializer::class)
               var createdDate: LocalDateTime = LocalDateTime.now()) {

    constructor(writer: String, title: String, contents: String): this(0, writer, title, contents)

    fun update(question: Question) {
        this.writer = question.writer
        this.title = question.title
        this.contents = question.contents
        this.countOfAnswer = question.countOfAnswer
    }

    fun getCreatedDate(): String = createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    fun isSameUser(user: User?): Boolean {
        return user!!.isSameUser(this.writer)
    }

    fun isSameUser(writer: String): Boolean {
        return this.writer == writer

    }

}