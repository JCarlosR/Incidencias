package com.youtube.sorcjc.incidencias.io.response;

import com.google.gson.annotations.SerializedName;
import com.youtube.sorcjc.incidencias.model.User;

public class LoginResponse {

    private boolean error;

    private User user;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
