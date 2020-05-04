package app.dao

import app.model.Question
import core.jdbc.JdbcTemplate

class QuestionDao {

    private val jdbcTemplate = JdbcTemplate()

    fun insert(question: Question) {

        val sql = "INSERT INTO " +
                "QUESTIONS (writer, title, contents, countOfAnswer, createdDate) " +
                "VALUES (?, ?, ?, ?, ?)"
        jdbcTemplate.update(sql,
                question.writer,
                question.title,
                question.contents,
                question.countOfAnswer,
                question.createdDate)
    }

    fun findById(questionId: Long): Question? {
        val sql = "SELECT questionId, writer, title, contents, countOfAnswer, createdDate " +
                "FROM QUESTIONS " +
                "WHERE questionId = ?"

        return jdbcTemplate.queryObject(sql, questionId) {
            Question(
                    it.getLong("questionId"),
                    it.getString("writer"),
                    it.getString("title"),
                    it.getString("contents"),
                    it.getInt("countOfAnswer"),
                    it.getTimestamp("createdDate").toLocalDateTime())

        }
    }

    fun findAll(): List<Question> {
        val sql = "SELECT questionId, writer, title, contents, countOfAnswer, createdDate " +
                "FROM QUESTIONS"
        return jdbcTemplate.query(sql) {
            Question(
                    it.getLong("questionId"),
                    it.getString("writer"),
                    it.getString("title"),
                    it.getString("contents"),
                    it.getInt("countOfAnswer"),
                    it.getTimestamp("createdDate").toLocalDateTime())
        }
    }

    fun update(question: Question) {
        val sql = "UPDATE QUESTIONS " +
                "SET writer = ?, title = ?, contents= ?, countOfAnswer = ? " +
                "WHERE questionId = ?"
        jdbcTemplate.update(sql,
                question.writer,
                question.title,
                question.contents,
                question.countOfAnswer,
                question.questionId)

    }

    fun incrementCountOfAnswer(questionId: Long) {
        val sql = "UPDATE QUESTIONS " +
                "SET countOfAnswer = countOfAnswer + 1 " +
                "WHERE questionId = ?"
        jdbcTemplate.update(sql, questionId)

    }

    fun delete(questionId: Long) {
        val sql = "DELETE FROM QUESTIONS WHERE questionId = ?"
        jdbcTemplate.update(sql, questionId)
    }

    companion object {
        private val QUESTION_DAO =  QuestionDao()

        fun getInstance(): QuestionDao {
            return QUESTION_DAO
        }
    }

}