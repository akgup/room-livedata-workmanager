package com.buzzybrains.mvvmarchitecture.services

import com.buzzybrains.mvvmarchitecture.model.Note

interface ISyncService {

    fun syncNote(note: Note)
}
