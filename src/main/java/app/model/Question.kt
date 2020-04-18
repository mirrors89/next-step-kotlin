package app.model

import java.time.LocalDateTime

data class Question(var questionId: Long,
               var writer: String,
               var title: String,
               var contents: String,
               var countOfAnswer: Int = 0,
               var createdDate: LocalDateTime = LocalDateTime.now()) {

    fun update(question: Question) {
        this.writer = question.writer
        this.title = question.title
        this.contents = question.contents
        this.countOfAnswer = question.countOfAnswer
    }



}