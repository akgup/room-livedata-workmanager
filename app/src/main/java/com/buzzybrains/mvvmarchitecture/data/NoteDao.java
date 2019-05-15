package com.buzzybrains.mvvmarchitecture.data;


import com.buzzybrains.mvvmarchitecture.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    long insert(Note note);


    @Update
    void update(Note note);


    @Delete
    void delete(Note note);


    @Query("DELETE from note_table")
    void deleteAllNotes();


    @Query("SELECT *  from note_table ORDER by priority desc")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT *  from note_table where isSync=0")
    List<Note> getAsyncNotes();


}
