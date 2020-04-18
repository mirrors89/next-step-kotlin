package app.model

import java.time.LocalDateTime

data class Answer(var answerId: Long? = null,
                  var questionId: Long,
                  var writer: String,
                  var contents: String,
                  var createdDate: LocalDateTime = LocalDateTime.now()
) {


    constructor(questionId: Long, writer: String, contents: String) :
            this(0, questionId, writer, contents, LocalDateTime.now())

}