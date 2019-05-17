package com.buzzybrains.mvvmarchitecture.model


import java.io.Serializable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
 data class Note (var title: String? = null,
             var description: String? = null,
             var priority: Int = 0 ,
             var isSync: Boolean = false): Serializable {

    @PrimaryKey(autoGenerate = true) var id: Long = 0


    override fun toString(): String {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", priority=" + priority +
                ", isSync=" + isSync +
                '}'.toString()
    }
}
