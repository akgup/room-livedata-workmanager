package com.buzzybrains.mvvmarchitecture.asynctask

import android.os.AsyncTask

import com.buzzybrains.mvvmarchitecture.data.NoteDao
import com.buzzybrains.mvvmarchitecture.model.Note

class DeleteNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Note, Void, Void>() {

    override fun doInBackground(vararg notes: Note): Void? {

        noteDao.delete(notes[0])

        return null

    }
}
