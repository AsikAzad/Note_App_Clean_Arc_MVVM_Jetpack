package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case

import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.repository.NoteRepo

class DeleteNote(private val repo: NoteRepo) {
    suspend operator fun invoke(note: Note){
        repo.deleteNote(note)
    }
}