package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes

import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.NoteOrder
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
