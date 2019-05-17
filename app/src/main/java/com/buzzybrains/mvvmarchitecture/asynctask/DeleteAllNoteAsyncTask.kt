package com.buzzybrains.mvvmarchitecture.asynctask

import android.os.AsyncTask

import com.buzzybrains.mvvmarchitecture.data.NoteDao

class DeleteAllNoteAsyncTask(private val noteDao: NoteDao) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg voids: Void): Void? {

        noteDao.deleteAllNotes()

        return null

    }
}

