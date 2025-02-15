package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.Option
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.domain.model.SurveyType
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOnBoardingQuestionUseCase @Inject constructor(private val surveyRepository: SurveyRepository) {
    suspend operator fun invoke(): Flow<List<RoundQuestion>> {
        val response = surveyRepository.getOnboarding()
        return flow {
            if (response.result == ResponseResult.SUCCESS.name) {
                response.data?.surveyResponses?.map {
                    RoundQuestion(
                        id = it.id.toString(),
                        surveyType = it.surveyType.mapToSurveyType(),
                        content = it.contents,
                        options = it.options.map {
                            Option(it.id.toString(), it.optionContents)
                        }
                    )
                }
            } else {
                throw Exception(response.error?.message)
            }
        }
    }

    fun String?.mapToSurveyType(): SurveyType {
        if (this == SurveyType.BALANCE.name) {
            return SurveyType.BALANCE
        } else {
            return SurveyType.MULTIPLE_CHOICE
        }
    }
}
