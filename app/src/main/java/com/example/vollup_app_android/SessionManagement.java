package com.example.vollup_app_android;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    //Access shared preferences on constructor
    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    //Add session id to shared preferences
    public void saveSession(String id){
        //save session of user whenever user is logged in
        editor.putString(SESSION_KEY,id).commit();
    }

    //Check if shared preferences session id is saved.
    public String getSession(){
        //return user whose session is saved
        return sharedPreferences.getString(SESSION_KEY, "-1");
    }

    //Remove shared preferences session id.
    public void removeSession(){
        editor.putString(SESSION_KEY,"-1").commit();
    }
}