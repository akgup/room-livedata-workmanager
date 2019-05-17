package com.buzzybrains.mvvmarchitecture.data

import android.app.Application

import com.buzzybrains.mvvmarchitecture.asynctask.DeleteAllNoteAsyncTask
import com.buzzybrains.mvvmarchitecture.asynctask.DeleteNoteAsyncTask
import com.buzzybrains.mvvmarchitecture.asynctask.InsertNoteAsyncTask
import com.buzzybrains.mvvmarchitecture.asynctask.UpdateNoteAsyncTask
import com.buzzybrains.mvvmarchitecture.model.Note
import com.buzzybrains.mvvmarchitecture.serviceimpl.SyncServiceImpl
import com.buzzybrains.mvvmarchitecture.services.INoteService
import com.buzzybrains.mvvmarchitecture.services.ISyncService

import androidx.lifecycle.LiveData

class NoteRepository(application: Application) : INoteService {

    private val noteDao: NoteDao

    val allNotes: LiveData<List<Note>>

    private val remoteSyncService: ISyncService


    val asyncNotes: List<Note>
        get() = noteDao.asyncNotes


    init {
        val noteDatabase = NoteDatabase.getInstance(application)

        noteDao = noteDatabase.noteDao()

        allNotes = noteDao.allNotes

        remoteSyncService = SyncServiceImpl(this)

    }

    fun syncNoteData(note: Note) {
        remoteSyncService.syncNote(note)
    }


    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao, this).execute(note)
    }

    fun update(note: Note) {


        UpdateNoteAsyncTask(noteDao, this).execute(note)
    }


    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNoteAsyncTask(noteDao).execute()
    }


    override fun getSyncSuccessNoteObject(note: Note) {
        note.isSync = true

        UpdateNoteAsyncTask(noteDao, this).execute(note)

    }

    override fun getSyncFailureNoteObject(note: Note) {

    }


}
