package com.wasem.tower_administration.interfaces;

import java.util.List;

public interface ListCallback<Model> {

    void onSuccess(List<Model> list);

    void onFailure(String message);
}
