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
        val data = surveyRepository.getSurvey(bundleId)
        return flow {
            if (data.result == ResponseResult.SUCCESS.name) {
                val result = RoundQuestion(
                    id = data.data?.id.toString(),
                    surveyType = data.data?.surveyType.mapToSurveyType(),
                    content = data.data?.contents ?: "",
                    options = data.data?.options?.map {
                        Option(it.id.toString(), it.optionContents)
                    } ?: emptyList()
                )
                emit(result)
            } else {
                throw Exception(data.error?.message)
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
