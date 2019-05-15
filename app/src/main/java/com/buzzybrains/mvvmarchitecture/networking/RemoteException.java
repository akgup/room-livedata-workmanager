package com.buzzybrains.mvvmarchitecture.networking;

import retrofit2.Response;

public class RemoteException extends Exception {

    private Response response;

    public RemoteException(Response response) {

        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
