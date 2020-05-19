package app.dao

import app.model.Answer
import java.util.stream.Collectors

class MockAnswerDao : AnswerDao {
    private val answers: HashMap<Long, Answer> = hashMapOf()

    override fun insert(answer: Answer): Answer? {
        return answers.put(answer.answerId!!, answer)
    }

    override fun findById(answerId: Long): Answer? {
        return answers[answerId]
    }

    override fun findAllByQuestionId(questionId: Long): List<Answer> {
        return answers.values.stream().filter { a: Answer? -> a!!.questionId == questionId }
                .collect(Collectors.toList())
    }

    override fun delete(answerId: Long) {
        answers.remove(answerId)
    }
}