package pl.mazi.ledapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.settings.MySettings;

import java.util.List;
import java.util.Locale;


// TODO REBUILD ALL SETINGS
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        initInputValue();
    }

    private void initInputValue() {
        EditTextPreference ledNumPref = getPreferenceManager().findPreference("LED_NUM");
        ledNumPref.setOnBindEditTextListener((editText -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }));

        EditTextPreference dataPinPref = getPreferenceManager().findPreference("DATA_PIN");
        dataPinPref.setOnBindEditTextListener((editText -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }));

        EditTextPreference brightnessPref = getPreferenceManager().findPreference("INIT_BRIGHTNESS");
        brightnessPref.setOnBindEditTextListener((editText -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }));

        ListPreference language = getPreferenceManager().findPreference("LANGUAGE");
        language.setOnPreferenceChangeListener((preference, newValue) -> {
            MySettings mySettings = new MySettings(getContext());
            mySettings.loadLanguage(newValue.toString());
            return true;
        });

    }
}