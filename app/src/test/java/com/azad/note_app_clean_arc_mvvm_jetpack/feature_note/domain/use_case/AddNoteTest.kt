package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case

import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.data.repository.FakeNoteRepo
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.InvalidNoteException
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.NoteOrder
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.fail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest{
    private lateinit var fakeNoteRepo: FakeNoteRepo
    private lateinit var getNotes: GetNotes

    @Before
    fun setUp(){
        fakeNoteRepo = FakeNoteRepo()
        getNotes = GetNotes(fakeNoteRepo)
    }

    @Test
    fun `Note insert successful`() = runBlocking{
        //Get existing notes list
        val prevNotes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        //Create new note
        val note = Note(
            title = "Greetings",
            content = "Hello World",
            timestamp = 10.toLong(),
            color = 100
        )

        //Add new note
        fakeNoteRepo.insertNote(note)

        //Get latest notes list
        val newNotes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        //Check for note list size increment
        assertThat(prevNotes.size).isLessThan(newNotes.size)
    }

    @Test
    fun `Blank note insert throws exception`() = runBlocking{

        //Create blank note
        val note = Note(
            title = "",
            content = "Hello World",
            timestamp = 10.toLong(),
            color = 100
        )

        //Add blank note
        try {
            fakeNoteRepo.insertNote(note)
            fail("Fail to throw exception")
        }catch (exception:InvalidNoteException){
            //Check for insert exception
            assertThat(exception).hasMessageThat().contains("The title of the note can't be empty.")
        }
    }

    @Test
    fun `Blank note content insert throws exception`() = runBlocking{

        //Create blank note
        val note = Note(
            title = "Greetings",
            content = "",
            timestamp = 10.toLong(),
            color = 100
        )

        //Add blank note
        try {
            fakeNoteRepo.insertNote(note)
            fail("Fail to throw exception")
        }catch (exception:InvalidNoteException){
            //Check for insert exception
            assertThat(exception).hasMessageThat().contains("The content of the note can't be empty.")
        }
    }
}