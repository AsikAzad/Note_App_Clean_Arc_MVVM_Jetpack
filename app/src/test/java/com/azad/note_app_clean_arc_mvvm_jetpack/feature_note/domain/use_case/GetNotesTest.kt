package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case

import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.data.repository.FakeNoteRepo
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.model.Note
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.NoteOrder
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {
    private lateinit var fakeNoteRepo: FakeNoteRepo
    private lateinit var getNotes: GetNotes

    @Before
    fun setUp(){
        fakeNoteRepo = FakeNoteRepo()
        getNotes = GetNotes(fakeNoteRepo)

        //Create test data
        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed{ index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = "Content of $c",
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }

        //Randomize the whole list
        notesToInsert.shuffle()

        //Insert data to the fake repository
        //runBlocking is for suspended function
        runBlocking {
            notesToInsert.forEach { fakeNoteRepo.insertNote(it) }
        }
    }

    @Test
    fun `Order notes by title asc, correct order`() = runBlocking{
        //first() is for listening to the 1st emission
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by title desc, correct order`() = runBlocking{
        //first() is for listening to the 1st emission
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size - 2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date asc, correct order`() = runBlocking{
        //first() is for listening to the 1st emission
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by date desc, correct order`() = runBlocking{
        //first() is for listening to the 1st emission
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by color asc, correct order`() = runBlocking{
        //first() is for listening to the 1st emission
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size - 2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `Order notes by color desc, correct order`() = runBlocking{
        //first() is for listening to the 1st emission
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size - 2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
}