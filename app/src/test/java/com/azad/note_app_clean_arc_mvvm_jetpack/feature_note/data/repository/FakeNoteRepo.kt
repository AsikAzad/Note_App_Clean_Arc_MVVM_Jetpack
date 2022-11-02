package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.data.repository

import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.InvalidNoteException
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.repository.NoteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepo : NoteRepo {

    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    @Throws(InvalidNoteException::class)
    override suspend fun insertNote(note: Note) {
        //Check for valid data
        if (note.title.isBlank()) throw InvalidNoteException("The title of the note can't be empty.")
        if (note.content.isBlank()) throw InvalidNoteException("The content of the note can't be empty.")

        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}