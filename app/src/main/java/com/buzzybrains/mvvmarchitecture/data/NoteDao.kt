package com.buzzybrains.mvvmarchitecture.data


import com.buzzybrains.mvvmarchitecture.model.Note

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {


    @get:Query("SELECT *  from note_table ORDER by priority desc")
    val allNotes: LiveData<List<Note>>

    @get:Query("SELECT *  from note_table where isSync=0")
    val asyncNotes: List<Note>

    @Insert
    fun insert(note: Note): Long


    @Update
    fun update(note: Note)


    @Delete
    fun delete(note: Note)


    @Query("DELETE from note_table")
    fun deleteAllNotes()


}
