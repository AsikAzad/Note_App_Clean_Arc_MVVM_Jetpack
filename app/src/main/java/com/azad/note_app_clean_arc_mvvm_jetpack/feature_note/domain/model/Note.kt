package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azad.note_app_clean_arc_mvvm_jetpack.ui.theme.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}


class InvalidNoteException(message: String): Exception(message)