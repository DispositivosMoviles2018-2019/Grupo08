package ec.edu.uce.tarea_final_2h_hguaman.vista;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import ec.edu.uce.tarea_final_2h_hguaman.R;


public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        /*
        CheckBoxPreference ascendente = (CheckBoxPreference) findPreference("asc");
        CheckBoxPreference descendente = (CheckBoxPreference) findPreference("des");

        if(ascendente.isChecked())
            descendente.setChecked(false);

        if(descendente.isChecked())
            ascendente.setChecked(false);*/
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @SuppressLint("ResourceType")
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.layout.preferencias);
        }
    }

}