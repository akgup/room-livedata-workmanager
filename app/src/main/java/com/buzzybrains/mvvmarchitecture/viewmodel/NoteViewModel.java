package com.buzzybrains.mvvmarchitecture.viewmodel;

import android.app.Application;

import com.buzzybrains.mvvmarchitecture.data.NoteRepository;
import com.buzzybrains.mvvmarchitecture.model.Note;
import com.buzzybrains.mvvmarchitecture.workmanager.SyncWorkManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    private LiveData<List<Note>> allNotes;


    private WorkManager mWorkManager;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);

        allNotes = noteRepository.getAllNotes();

        mWorkManager = WorkManager.getInstance();
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }


    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deletAllNotes() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void startSync() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .build();

        OneTimeWorkRequest noteWorker = new OneTimeWorkRequest.Builder(SyncWorkManager.class).setConstraints(constraints).build();

        mWorkManager.enqueue(noteWorker);
    }
}
