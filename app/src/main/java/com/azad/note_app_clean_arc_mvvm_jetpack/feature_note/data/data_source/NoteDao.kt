package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.data.data_source

import androidx.room.*
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM NOTE")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE ID = :id")
    suspend fun getNoteById(id:Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}