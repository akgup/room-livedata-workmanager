package com.buzzybrains.mvvmarchitecture.serviceimpl;

import com.buzzybrains.mvvmarchitecture.model.Note;
import com.buzzybrains.mvvmarchitecture.retrofit.RemoteNoteEndpoint;
import com.buzzybrains.mvvmarchitecture.retrofit.RemoteNoteService;
import com.buzzybrains.mvvmarchitecture.services.INoteService;
import com.buzzybrains.mvvmarchitecture.services.ISyncService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncServiceImpl implements ISyncService {

    INoteService mService;

    public SyncServiceImpl(INoteService noteService) {
        this.mService = noteService;
    }

    @Override
    public void syncNote(final Note note) {

        RemoteNoteEndpoint service = RemoteNoteService.getClient().create(RemoteNoteEndpoint.class);

        Call<ResponseBody> call = service.addNote(note);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    note.setSync(true);

                    mService.getSyncSuccessNoteObject(note);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                mService.getSyncFailureNoteObject(note);

            }
        });


    }
}
