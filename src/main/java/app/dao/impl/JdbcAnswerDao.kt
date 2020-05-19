package app.dao.impl

import app.dao.AnswerDao
import app.model.Answer
import core.jdbc.JdbcTemplate
import core.jdbc.KeyHolder
import core.jdbc.PreparedStatementCreator
import java.sql.Statement
import java.sql.Timestamp


class JdbcAnswerDao : AnswerDao {

    private val jdbcTemplate = JdbcTemplate()

    override fun insert(answer: Answer) : Answer? {
        val jdbcTemplate = JdbcTemplate()
        val sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)"

        val psc = PreparedStatementCreator { con ->
            val pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            pstmt.setString(1, answer.writer)
            pstmt.setString(2, answer.contents)
            pstmt.setTimestamp(3, Timestamp.valueOf(answer.createdDate))
            pstmt.setLong(4, answer.questionId)
            pstmt
        }

        val keyHolder = KeyHolder()
        jdbcTemplate.update(psc, keyHolder)
        return findById(keyHolder.id)
    }

    override fun findById(answerId: Long): Answer? {
        val sql = "SELECT answerId, writer, contents, createdDate, questionId " +
                "FROM ANSWERS " +
                "WHERE answerId = ?"

        return jdbcTemplate.queryObject(sql, answerId) {
            Answer(
                    it.getLong("answerId"),
                    it.getLong("questionId"),
                    it.getString("writer"),
                    it.getString("contents"),
                    it.getTimestamp("createdDate").toLocalDateTime())
        }
    }

    override fun findAllByQuestionId(questionId: Long): List<Answer> {
        val sql = ("SELECT answerId, questionId, writer, contents, createdDate FROM ANSWERS " +
                "WHERE questionId = ? " +
                "order by answerId desc")

        return jdbcTemplate.query(sql, questionId) {
            Answer(
                    it.getLong("answerId"),
                    it.getLong("questionId"),
                    it.getString("writer"),
                    it.getString("contents"),
                    it.getTimestamp("createdDate").toLocalDateTime())
        }
    }

    override fun delete(answerId: Long) {
        val sql = "DELETE FROM ANSWERS WHERE answerId = ?"

        jdbcTemplate.update(sql, answerId)
        return jdbcTemplate.update(sql, answerId)
    }
}