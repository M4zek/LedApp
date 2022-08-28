package pl.mazi.ledapp.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import pl.mazi.ledapp.R;

import java.util.Locale;

public class MySettings{
    private SharedPreferences sharedPreferences;

    private Context ctx;
    public MySettings(Context context) {
        this.ctx = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean turnLedAfterConnected(){
        return sharedPreferences.getBoolean("LED_TURN_ON",false);
    }

    public int initBrightness(){
        return sharedPreferences.getInt("INIT_BRIGHTNESS",50);
    }

    public int resetPin() { return sharedPreferences.getInt("RESET_PIN",5); }

    public String initColor(){
        return sharedPreferences.getString("INIT_COLOR","16774657");
    }

    public boolean nightMode(){
        return sharedPreferences.getBoolean("NIGHT_MOD",true);
    }

    public String language(){
        return sharedPreferences.getString("LANGUAGE","en");
    }

    public boolean turnOnBluetoothAuto(){
        return sharedPreferences.getBoolean("AUTO_ON_BT",false);
    }

    public boolean connectToDeviceAuto(){
        return sharedPreferences.getBoolean("BT_AUTO_CONNECT",false);
    }

//    public String deviceName(){
//        return sharedPreferences.getString("DEVICE_NAME","HC-05");
//    }


    public void setTheme(){
        if(sharedPreferences.getBoolean("NIGHT_MOD", true)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void loadLanguage(){
        Locale local = new Locale(language());
        Locale.setDefault(local);

        Configuration configuration = new Configuration();
        configuration.locale = local;

        ctx.getResources().updateConfiguration(configuration,ctx.getResources().getDisplayMetrics());
    }
}
