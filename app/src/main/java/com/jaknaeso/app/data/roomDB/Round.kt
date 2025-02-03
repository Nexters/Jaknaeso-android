package com.jaknaeso.app.data.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "round_table")
data class Round(
    @PrimaryKey val roundIndex: Int,
    val isLocked: Boolean,
    val isCompleted: Boolean,
    val isTodayQuestion: Boolean
)
