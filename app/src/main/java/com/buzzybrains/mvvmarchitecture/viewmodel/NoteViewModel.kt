package com.buzzybrains.mvvmarchitecture.viewmodel

import android.app.Application

import com.buzzybrains.mvvmarchitecture.data.NoteRepository
import com.buzzybrains.mvvmarchitecture.model.Note
import com.buzzybrains.mvvmarchitecture.workmanager.SyncWorkManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var isNoteClicked = MutableLiveData<Boolean>()
    var noteMutableLiveData = MutableLiveData<Note>()
    private val noteRepository: NoteRepository
    val allNotes: LiveData<List<Note>>
    private val mWorkManager: WorkManager

    init {

        noteRepository = NoteRepository(application)

        allNotes = noteRepository.allNotes

        mWorkManager = WorkManager.getInstance()
    }

    fun insert(note: Note) {
        noteRepository.insert(note)
    }

    fun update(note: Note) {
        noteRepository.update(note)
    }


    fun delete(note: Note) {
        noteRepository.delete(note)
    }

    fun deletAllNotes() {
        noteRepository.deleteAllNotes()
    }

    fun startSync() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .build()

        val noteWorker = OneTimeWorkRequest.Builder(SyncWorkManager::class.java).setConstraints(constraints).build()

        mWorkManager.enqueue(noteWorker)
    }
}
