package team.exp.dimagsekhelo.Database.DataLogic;

import android.content.Context;
import android.content.SharedPreferences;

import team.exp.dimagsekhelo.Utils.Codes;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage implements Codes
{
    private Context  context;

    public LocalStorage(Context context){
        this.context = context;
    }

    public void storeUserInformation(String key, String value){
        SharedPreferences pref = context.getSharedPreferences(USER_INFORMATION_PREFERENCE, MODE_PRIVATE); // 0 - for private mode
        pref.edit().putString(key,value).apply();
    }

    public String getUserInformation(String key){
        SharedPreferences shared = context.getSharedPreferences(USER_INFORMATION_PREFERENCE, MODE_PRIVATE);
        return (shared.getString(key, ""));
    }
}
