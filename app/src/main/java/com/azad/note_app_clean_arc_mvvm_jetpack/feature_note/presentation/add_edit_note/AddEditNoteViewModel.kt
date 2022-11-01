package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.InvalidNoteException
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _noteTitleState = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitleState: State<NoteTextFieldState> = _noteTitleState

    private val _noteContentState = mutableStateOf(NoteTextFieldState(hint = "Enter some content"))
    val noteContentState: State<NoteTextFieldState> = _noteContentState

    private val _noteColorState = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColorState: State<Int> = _noteColorState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        //Get note id from prev activity
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getANote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitleState.value = noteTitleState.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContentState.value = noteContentState.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColorState.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitleState.value = noteTitleState.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitleState.value = noteTitleState.value.copy(isHintVisible = !event.focusState.isFocused && noteTitleState.value.text.isBlank())
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContentState.value = noteContentState.value.copy(text = event.value)
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContentState.value = noteContentState.value.copy(isHintVisible = !event.focusState.isFocused && noteContentState.value.text.isBlank())
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColorState.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitleState.value.text,
                                content = noteContentState.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColorState.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message ?: "Couldn't save note!"))
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}