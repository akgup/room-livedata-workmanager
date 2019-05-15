package com.buzzybrains.mvvmarchitecture.asynctask;

import android.os.AsyncTask;

import com.buzzybrains.mvvmarchitecture.data.NoteDao;

public class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

    private NoteDao noteDao;

    public DeleteAllNoteAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;

    }

    @Override
    protected Void doInBackground(Void... voids) {

        noteDao.deleteAllNotes();

        return null;

    }
}

