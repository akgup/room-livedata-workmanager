package com.buzzybrains.mvvmarchitecture.asynctask;

import android.os.AsyncTask;

import com.buzzybrains.mvvmarchitecture.data.NoteDao;
import com.buzzybrains.mvvmarchitecture.model.Note;
import com.buzzybrains.mvvmarchitecture.serviceimpl.SyncServiceImpl;
import com.buzzybrains.mvvmarchitecture.services.INoteService;
import com.buzzybrains.mvvmarchitecture.services.ISyncService;

public class InsertNoteAsyncTask extends AsyncTask<Note, Void, Note> {

    static ISyncService remoteSyncService;
    private NoteDao noteDao;

    public InsertNoteAsyncTask(NoteDao noteDao, INoteService service) {
        this.noteDao = noteDao;

        remoteSyncService = new SyncServiceImpl(service);
    }

    @Override
    protected Note doInBackground(Note... notes) {

        Note note = notes[0];

        long generatedId = noteDao.insert(note);

        note.setId(generatedId);

        return note;

    }

    @Override
    protected void onPostExecute(Note note) {
        super.onPostExecute(note);

        remoteSyncService.syncNote(note);

    }
}
