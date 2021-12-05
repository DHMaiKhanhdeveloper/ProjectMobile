package com.example.fer_medindex.view;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PatientFormInput {
    private static SharedPreferences pref;
    private static Gson gson = new Gson();

    public static void createSharedPreferences(Context context) {
        pref = context.getSharedPreferences("FER", Context.MODE_PRIVATE);
    }

    public static ReadWritePatientDetails getForm() {
        String value = pref.getString(PATIENT_FORM, "");
        if(value.isEmpty()) {
            return null;
        }
        return gson.fromJson(value, ReadWritePatientDetails.class);
    }

    public static void createFrom(ReadWritePatientDetails readWritePatientDetails) {
        pref.edit().putString(PATIENT_FORM, gson.toJson(readWritePatientDetails)).apply();
    }

    public static void clearForm() {
        pref.edit().remove(PATIENT_FORM).apply();
    }

    private static String PATIENT_FORM = "PATIENT_FORM";
}
