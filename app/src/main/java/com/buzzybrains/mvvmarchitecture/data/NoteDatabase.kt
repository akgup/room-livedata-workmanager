package com.buzzybrains.mvvmarchitecture.data

import android.content.Context
import android.os.AsyncTask

import com.buzzybrains.mvvmarchitecture.model.Note
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao



    companion object {

        private var instance: NoteDatabase? = null

        private val roomCallback = object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                PopulateDbAsyncTask(instance!!).execute()
            }
        }


        @Synchronized
        @JvmStatic
        fun getInstance(context: Context): NoteDatabase {

            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java, "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }

            return instance!!

        }
    }
    class PopulateDbAsyncTask  constructor(db: NoteDatabase) : AsyncTask<Void, Void, Void>() {

        private val noteDao: NoteDao

        init {
            noteDao = db.noteDao()

        }

        override fun doInBackground(vararg voids: Void): Void? {

            noteDao.insert(Note("T1", "D1", 1, false))
            noteDao.insert(Note("T2", "D2", 2, false))
            noteDao.insert(Note("T3", "D3", 3, false))
            noteDao.insert(Note("T4", "D4", 4, false))

            return null

        }
    }



}
