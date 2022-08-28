package pl.mazi.ledapp.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.*;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.R;

import java.util.Locale;


// TODO IN THE FUTURE
// - Data pin support
// - Number of leds support
// - Fixing language and theme switching

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        initOnChangeValue();
    }

    private void initOnChangeValue() {
//        EditTextPreference ledNumPref = getPreferenceManager().findPreference("LED_NUM");
//        ledNumPref.setOnBindEditTextListener((editText -> {
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
//        }));

        ListPreference language = getPreferenceManager().findPreference("LANGUAGE");
        language.setOnPreferenceChangeListener((preference, newValue) -> {
            Locale local = new Locale(newValue.toString());
            Locale.setDefault(local);
            Configuration configuration = new Configuration();
            configuration.locale = local;
            getContext().getResources().updateConfiguration(configuration,getContext().getResources().getDisplayMetrics());
            return true;
        });
    }
}