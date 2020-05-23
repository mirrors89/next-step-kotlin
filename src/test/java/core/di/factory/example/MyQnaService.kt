package core.di.factory.example

import core.annotation.Inject
import core.annotation.Service


@Service
class MyQnaService @Inject constructor(private val userRepository: UserRepository, private val questionRepository: QuestionRepository) {

    fun getUserRepository(): UserRepository? {
        return userRepository
    }

    fun getQuestionRepository(): QuestionRepository? {
        return questionRepository
    }

}