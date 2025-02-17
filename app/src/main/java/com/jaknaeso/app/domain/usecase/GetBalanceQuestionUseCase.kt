package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.Option
import com.jaknaeso.app.domain.model.RoundQuestion
import com.jaknaeso.app.domain.model.SurveyType
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBalanceQuestionUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {

    suspend operator fun invoke(bundleId: String): Flow<RoundQuestion?> {
        val response = surveyRepository.getSurvey(bundleId)
        return flow {
            if (response?.result == ResponseResult.ERROR.name) {
                throw Exception(response?.error?.message)
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                throw Exception(response.result)
            } else {
                val result = RoundQuestion(
                    surveyId = response?.data?.id.toString(),
                    surveyType = response?.data?.surveyType.mapToSurveyType(),
                    content = response?.data?.contents ?: "",
                    options = response?.data?.options?.map {
                        Option(it.id.toString(), it.optionContents)
                    } ?: emptyList()
                )
                emit(result)
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
