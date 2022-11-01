package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getANote: GetANote
)
