package com.wasem.tower_administration.interfaces;

public interface ProcessCallback {

    void onSuccess(String message);

    void onFailure(String message);
}
