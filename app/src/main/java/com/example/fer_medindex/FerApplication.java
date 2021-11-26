package com.example.fer_medindex;

import android.app.Application;

public class FerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PatientFormInput.createSharedPreferences(this);
    }
}
