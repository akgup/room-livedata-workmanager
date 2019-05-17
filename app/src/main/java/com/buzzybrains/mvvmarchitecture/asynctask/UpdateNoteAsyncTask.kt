package com.buzzybrains.mvvmarchitecture.asynctask

import android.os.AsyncTask

import com.buzzybrains.mvvmarchitecture.data.NoteDao
import com.buzzybrains.mvvmarchitecture.model.Note
import com.buzzybrains.mvvmarchitecture.serviceimpl.SyncServiceImpl
import com.buzzybrains.mvvmarchitecture.services.INoteService
import com.buzzybrains.mvvmarchitecture.services.ISyncService

class UpdateNoteAsyncTask(private val noteDao: NoteDao, service: INoteService) : AsyncTask<Note, Void, Note>() {

    internal var remoteSyncService: ISyncService

    init {

        remoteSyncService = SyncServiceImpl(service)
    }


    override fun doInBackground(vararg notes: Note): Note {

        val note = notes[0]

        noteDao.update(note)

        return note

    }

    override fun onPostExecute(note: Note) {
        super.onPostExecute(note)

        if (!note.isSync) {
            remoteSyncService.syncNote(note)
        }


    }


}
