package com.buzzybrains.mvvmarchitecture.asynctask;

import android.os.AsyncTask;

import com.buzzybrains.mvvmarchitecture.data.NoteDao;
import com.buzzybrains.mvvmarchitecture.model.Note;

public class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao noteDao;

    public DeleteNoteAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;

    }

    @Override
    protected Void doInBackground(Note... notes) {

        noteDao.delete(notes[0]);

        return null;

    }
}
