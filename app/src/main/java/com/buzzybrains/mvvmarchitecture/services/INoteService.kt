package com.buzzybrains.mvvmarchitecture.services

import com.buzzybrains.mvvmarchitecture.model.Note

interface INoteService {

    fun getSyncSuccessNoteObject(note: Note)

    fun getSyncFailureNoteObject(note: Note)
}
