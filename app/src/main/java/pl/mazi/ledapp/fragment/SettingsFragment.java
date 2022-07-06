package pl.mazi.ledapp.fragment;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import pl.mazi.ledapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}