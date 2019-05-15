package com.buzzybrains.mvvmarchitecture.workmanager;

import android.app.Application;
import android.content.Context;

import com.buzzybrains.mvvmarchitecture.data.NoteRepository;
import com.buzzybrains.mvvmarchitecture.model.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncWorkManager extends Worker {

    NoteRepository noteRepository;

    public SyncWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        noteRepository = new NoteRepository((Application) getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {

        List<Note> asyncNotes = noteRepository.getAsyncNotes();

        for (Note note : asyncNotes) {
            noteRepository.syncNoteData(note);
        }

        return Result.success();
    }
}
