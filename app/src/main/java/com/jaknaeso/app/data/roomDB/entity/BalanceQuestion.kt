package com.jaknaeso.app.data.roomDB.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "balance_question_table",
    foreignKeys = [
        ForeignKey(
            entity = Round::class,
            parentColumns = ["roundIndex"],
            childColumns = ["roundIndex"],
            onDelete = ForeignKey.CASCADE // Round 삭제 시 관련 질문 삭제
        )
    ],
    indices = [Index(value = ["roundIndex"])]
)
data class BalanceQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 개별 id 추가
    val roundIndex: Int,
    val question: String,
    val options: List<String>
)
