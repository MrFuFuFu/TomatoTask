package com.android.tomatotask;


import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
//import android.preference.Preference;
//import android.preference.PreferenceScreen;
//import android.preference.RingtonePreference;
import android.support.v4.preference.PreferenceFragment;

public class SettingPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	ListPreference lstPre_TomatoTime_value, lstPre_BreakTime_value;
	public SettingPreferenceFragment() {
		// TODO 自动生成的构造函数存根
	}
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO 自动生成的方法存根
		super.onCreate(paramBundle);
		addPreferencesFromResource(R.xml.preferences);
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
		prefs.registerOnSharedPreferenceChangeListener(this);
		lstPre_TomatoTime_value=(ListPreference)findPreference("TomatoTime_value");
		lstPre_BreakTime_value=(ListPreference)findPreference("BreakTime_value");
		lstPre_TomatoTime_value.setSummary(lstPre_TomatoTime_value.getEntry());
		lstPre_BreakTime_value.setSummary(lstPre_BreakTime_value.getEntry());
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO 自动生成的方法存根
		if (key.equals("TomatoTime_value")) {
			lstPre_TomatoTime_value.setSummary(lstPre_TomatoTime_value.getEntry());
		}
		if (key.equals("BreakTime_value")) {
			lstPre_BreakTime_value.setSummary(lstPre_BreakTime_value.getEntry());
		}
		
	}
	

}
