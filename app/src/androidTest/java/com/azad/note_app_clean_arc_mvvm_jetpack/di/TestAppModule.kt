package com.azad.note_app_clean_arc_mvvm_jetpack.di

import android.app.Application
import androidx.room.Room
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.data.data_source.NoteDB
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.data.repository.NoteRepoImpl
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.repository.NoteRepo
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDB(application: Application): NoteDB{
        return Room.inMemoryDatabaseBuilder(application, NoteDB::class.java).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepo(db: NoteDB): NoteRepo{
        return NoteRepoImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repo: NoteRepo): NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(repo),
            deleteNote = DeleteNote(repo),
            addNote = AddNote(repo),
            getANote = GetANote(repo)
        )
    }
}