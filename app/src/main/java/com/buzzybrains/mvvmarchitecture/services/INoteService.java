package com.buzzybrains.mvvmarchitecture.services;

import com.buzzybrains.mvvmarchitecture.model.Note;

public interface INoteService {


    void getSyncSuccessNoteObject(Note note);

    void getSyncFailureNoteObject(Note note);
}
