package com.wasem.tower_administration.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.wasem.tower_administration.AppController;
import com.wasem.tower_administration.Models.Admin;


enum PrefKeys {
    id, loggedIn, name, email, towerName, token
}

public class AppSharedPreferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private AppSharedPreferences() {
        sharedPreferences = AppController.getInstance().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    private static AppSharedPreferences instance;

    public static AppSharedPreferences getInstance() {
        if (instance == null) {
            instance = new AppSharedPreferences();
        }

        return instance;
    }

    public void save(Admin admin) {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefKeys.loggedIn.name(), true);
        editor.putInt(PrefKeys.id.name(), admin.id);
        editor.putString(PrefKeys.name.name(), admin.name);
        editor.putString(PrefKeys.email.name(), admin.email);
        editor.putString(PrefKeys.towerName.name(), admin.towerName);
        editor.putString(PrefKeys.token.name(), "Bearer " + admin.token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(PrefKeys.token.name(), "");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(PrefKeys.loggedIn.name(), false);
    }

    public void clear() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
