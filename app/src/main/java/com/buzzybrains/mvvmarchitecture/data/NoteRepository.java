package com.buzzybrains.mvvmarchitecture.data;

import android.app.Application;

import com.buzzybrains.mvvmarchitecture.asynctask.DeleteAllNoteAsyncTask;
import com.buzzybrains.mvvmarchitecture.asynctask.DeleteNoteAsyncTask;
import com.buzzybrains.mvvmarchitecture.asynctask.InsertNoteAsyncTask;
import com.buzzybrains.mvvmarchitecture.asynctask.UpdateNoteAsyncTask;
import com.buzzybrains.mvvmarchitecture.model.Note;
import com.buzzybrains.mvvmarchitecture.serviceimpl.RemoteSyncServiceImpl;
import com.buzzybrains.mvvmarchitecture.services.INoteService;
import com.buzzybrains.mvvmarchitecture.services.RemoteSyncService;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository implements INoteService {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    private RemoteSyncService remoteSyncService;


    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);

        noteDao = noteDatabase.noteDao();

        allNotes = noteDao.getAllNotes();


        remoteSyncService = new RemoteSyncServiceImpl(this);

    }

    public void syncNoteData(Note note) {
        remoteSyncService.syncNote(note);
    }


    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao, this).execute(note);
    }

    public void update(Note note) {


        new UpdateNoteAsyncTask(noteDao, this).execute(note);
    }


    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


    public List<Note> getAsyncNotes() {
        return noteDao.getAsyncNotes();
    }


    @Override
    public void getSyncSuccessNoteObject(Note note) {
        note.setSync(true);

        new UpdateNoteAsyncTask(noteDao, this).execute(note);

    }

    @Override
    public void getSyncFailureNoteObject(Note note) {

    }


}
