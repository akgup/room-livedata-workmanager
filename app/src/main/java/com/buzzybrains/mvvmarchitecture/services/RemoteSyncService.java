package com.buzzybrains.mvvmarchitecture.services;

import com.buzzybrains.mvvmarchitecture.model.Note;

public interface RemoteSyncService {

    void syncNote(Note note);
}
