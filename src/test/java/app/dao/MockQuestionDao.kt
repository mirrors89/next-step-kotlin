package app.dao

import app.model.Question
import java.util.stream.Collectors

class MockQuestionDao : QuestionDao {
    private val questions: HashMap<Long, Question> = hashMapOf()

    override fun insert(question: Question) {
        questions[question.questionId] = question
    }

    override fun findById(questionId: Long): Question? {
        return questions[questionId]
    }

    override fun findAll(): List<Question> {
        return questions.values.stream()
                .collect(Collectors.toList())
    }

    override fun update(question: Question) {
        questions[question.questionId] = question
    }

    override fun incrementCountOfAnswer(questionId: Long) {
        val question = questions[questionId]

        question!!.countOfAnswer = question.countOfAnswer + 1
        questions[questionId] = question
    }

    override fun delete(questionId: Long) {
        questions.remove(questionId)

    }

}