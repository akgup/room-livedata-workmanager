package com.buzzybrains.mvvmarchitecture.workmanager

import android.app.Application
import android.content.Context
import androidx.work.ListenableWorker

import com.buzzybrains.mvvmarchitecture.data.NoteRepository
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncWorkManager(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    internal var noteRepository: NoteRepository

    init {

        noteRepository = NoteRepository(applicationContext as Application)
    }

    override fun doWork(): ListenableWorker.Result {

        val asyncNotes = noteRepository.asyncNotes

        for (note in asyncNotes) {
            noteRepository.syncNoteData(note)
        }

        return ListenableWorker.Result.success()
    }
}
