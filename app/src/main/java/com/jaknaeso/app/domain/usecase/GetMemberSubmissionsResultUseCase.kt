package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.RoundResult
import com.jaknaeso.app.domain.repository.MemberRepository
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMemberSubmissionsResultUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(bundleId: String): Flow<List<RoundResult>> {
        val memberId = memberRepository.getMemberId().first()
        return flow {
            if (memberId != null) {
                val response = surveyRepository.getSubmissionsReport(bundleId = bundleId, memberId = memberId)
                if (response?.result == ResponseResult.ERROR.name) {
                    throw Exception(response.error?.message)
                } else {
                    val roundResult = response?.data?.surveyRecords?.mapIndexed { index, surveyRecord ->
                        RoundResult(
                            index = index,
                            question = surveyRecord.question,
                            answer = surveyRecord.answer,
                            word = surveyRecord.retrospective ?: "",
                            submittedAt = surveyRecord.submittedAt
                        )
                    } ?: emptyList()
                    emit(roundResult)
                }
            }
        }
    }
}
