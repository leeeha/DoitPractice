package com.tutorial.ch17_database

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val idPref = findPreference<EditTextPreference>("id")
        val colorPref = findPreference<ListPreference>("color")

        colorPref?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        idPref?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { pref ->
            val text = pref.text
            if(TextUtils.isEmpty(text)){
                "설정이 되지 않았습니다."
            }else{
                "설정된 id 값은: $text 입니다."
            }
        }
    }
}
