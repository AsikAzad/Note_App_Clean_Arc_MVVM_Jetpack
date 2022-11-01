package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case.NoteUseCases
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.NoteOrder
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel(){

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.Order -> {

                //Check for same sort call
                if (state.value.noteOrder::class == event.noteOrder::class && state.value.noteOrder.orderType == event.noteOrder.orderType) return


            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    //Temporary store delete note
                    recentlyDeletedNote = event.note
                    //Delete note
                    noteUseCases.deleteNote(event.note)
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        //Handle concurrent flow
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
                        .onEach {
                        notes->
                        _state.value = state.value.copy(notes, noteOrder)
                        }
                        .launchIn(viewModelScope)
    }
}