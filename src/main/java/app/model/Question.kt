package app.model

import app.CannotDeleteException
import app.support.LocalDateTimeDeserializer
import app.support.LocalDateTimeSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class Question(var questionId: Long,
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

    open fun canDelete(user: User, answers: List<Answer>): Boolean {
        if(!user.isSameUser(this.writer)) {
            throw CannotDeleteException("다른 사용자가 쓴 글을 삭제할 수 없습니다.")
        }

        for(answer: Answer in answers) {
            if(!answer.canDelete(user)) {
                throw CannotDeleteException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.")
            }
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (questionId != other.questionId) return false
        if (writer != other.writer) return false
        if (title != other.title) return false
        if (contents != other.contents) return false
        if (countOfAnswer != other.countOfAnswer) return false
        if (createdDate != other.createdDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = questionId.hashCode()
        result = 31 * result + writer.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + contents.hashCode()
        result = 31 * result + countOfAnswer
        result = 31 * result + createdDate.hashCode()
        return result
    }

}