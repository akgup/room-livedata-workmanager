package com.buzzybrains.mvvmarchitecture.serviceimpl

import com.buzzybrains.mvvmarchitecture.model.Note
import com.buzzybrains.mvvmarchitecture.retrofit.RemoteNoteEndpoint
import com.buzzybrains.mvvmarchitecture.retrofit.RemoteNoteService
import com.buzzybrains.mvvmarchitecture.services.INoteService
import com.buzzybrains.mvvmarchitecture.services.ISyncService

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncServiceImpl(internal var mService: INoteService) : ISyncService {

    override fun syncNote(note: Note) {

        val service = RemoteNoteService.client.create(RemoteNoteEndpoint::class.java)

        val call = service.addNote(note)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful && response.code() == 200) {
                    note.isSync = true

                    mService.getSyncSuccessNoteObject(note)

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                mService.getSyncFailureNoteObject(note)

            }
        })


    }
}
