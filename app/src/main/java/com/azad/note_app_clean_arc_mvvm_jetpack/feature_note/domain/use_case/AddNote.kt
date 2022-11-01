package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case

import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.InvalidNoteException
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.repository.NoteRepo

class AddNote(private val repo: NoteRepo) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        //Check for valid data
        if (note.title.isBlank()) throw InvalidNoteException("The title of the note can't be empty.")
        if (note.content.isBlank()) throw InvalidNoteException("The content of the note can't be empty.")

        //Insert data
        repo.insertNote(note)
    }
}