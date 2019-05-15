package com.buzzybrains.mvvmarchitecture.asynctask;

import android.os.AsyncTask;

import com.buzzybrains.mvvmarchitecture.data.NoteDao;
import com.buzzybrains.mvvmarchitecture.model.Note;
import com.buzzybrains.mvvmarchitecture.serviceimpl.RemoteSyncServiceImpl;
import com.buzzybrains.mvvmarchitecture.services.INoteService;
import com.buzzybrains.mvvmarchitecture.services.RemoteSyncService;

public class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Note> {

    static RemoteSyncService remoteSyncService;
    private NoteDao noteDao;

    public UpdateNoteAsyncTask(NoteDao noteDao, INoteService service) {
        this.noteDao = noteDao;

        remoteSyncService = new RemoteSyncServiceImpl(service);
    }


    @Override
    protected Note doInBackground(Note... notes) {

        Note note = notes[0];

        noteDao.update(note);

        return note;

    }

    @Override
    protected void onPostExecute(Note note) {
        super.onPostExecute(note);

        if (!note.isSync()) {
            remoteSyncService.syncNote(note);
        }


    }
}
