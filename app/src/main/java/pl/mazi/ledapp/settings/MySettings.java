package pl.mazi.ledapp.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class MySettings{
    private SharedPreferences sharedPreferences;

    private Context ctx;
    public MySettings(Context context) {
        this.ctx = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int numberOfLeds(){
        return sharedPreferences.getInt("LED_NUM",10);
    }

    public int dataPin(){
        return sharedPreferences.getInt("DATA_PIN",7);
    }

    public boolean turnLedAfterConnected(){
        return sharedPreferences.getBoolean("LED_TURN_ON",false);
    }

    public int initBrightness(){
        return Integer.parseInt(sharedPreferences.getString("INIT_BRIGHTNESS","50"));
    }

    public int initColor(){
        return sharedPreferences.getInt("INIT_COLOR",16774657);
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

    public String deviceName(){
        return sharedPreferences.getString("DEVICE_NAME","HC-05");
    }
}
