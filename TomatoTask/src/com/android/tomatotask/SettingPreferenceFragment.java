package com.android.tomatotask;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

public class SettingPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	ListPreference lstPre_TomatoTime_value, lstPre_BreakTime_value;
	public static final String RINGTONE    = "pref_ringtone";
	public static final String RINGTONE_TITLE_NAME = "pref_ringtone_name";
	private Preference mSoundsPref;
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
		
		mSoundsPref = findPreference("pref_ringtone");
		
		lstPre_TomatoTime_value=(ListPreference)findPreference("TomatoTime_value");
		lstPre_BreakTime_value=(ListPreference)findPreference("BreakTime_value");
		lstPre_TomatoTime_value.setSummary(lstPre_TomatoTime_value.getEntry());
		lstPre_BreakTime_value.setSummary(lstPre_BreakTime_value.getEntry());
		mSoundsPref.setSummary(prefs.getString(RINGTONE_TITLE_NAME, null));
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("TomatoTime_value")) {
			lstPre_TomatoTime_value.setSummary(lstPre_TomatoTime_value.getEntry());
		}
		if (key.equals("BreakTime_value")) {
			lstPre_BreakTime_value.setSummary(lstPre_BreakTime_value.getEntry());
		}
		if (key.equals("pref_ringtone")) {
			mSoundsPref.setSummary(sharedPreferences.getString(RINGTONE_TITLE_NAME, null));
		}
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO 自动生成的方法存根
		if (preference.getKey().equals("clearCount")) {
			alertDialogShow();
		}
		if (preference.getKey().equals("aboutTomatoTask")) {
			Uri uri = Uri.parse("http://baike.baidu.com/link?url=b7rlhS6YssFup2xqAjnw9__6VsQnyhtVT8Gx_-qwckUE4IZ-ns6i_jw9w_aKH-C_sjWheb9NFR_GZcfUII0bV_");
			startActivity(new Intent(Intent.ACTION_VIEW,uri));
		}
		if (preference.getKey().equals("supportAuthor")) {
			Uri uri = Uri.parse("https://m.alipay.com/personal/payment.htm?userId=2088302435546204&reason=%E6%94%AF%E6%8C%81%E8%BD%AF%E4%BB%B6%E5%BC%80%E5%8F%91&weChat=true");  
			startActivity(new Intent(Intent.ACTION_VIEW,uri));
		}
		if (mSoundsPref == preference) {
			doPickRingtone();
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	private void doPickRingtone() {
		SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
		String Str = sharedPreferences.getString(RINGTONE, null); 
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        // Allow user to pick 'Default'
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        // Show only ringtones
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        //set the default Notification value
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        // Don't show 'Silent'
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        Uri notificationUri;
        if (Str != null) {
        	notificationUri = Uri.parse(Str);
            // Put checkmark next to the current ringtone for this contact
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, notificationUri);//打开对话框的时候就默认选中上次被选中的音乐
            Log.v("MAIN", "aaaaa");
        } else {
            // Otherwise pick default ringtone Uri so that something is selected.
        	notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            // Put checkmark next to the current ringtone for this contact
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, notificationUri);
            Log.v("MAIN", "ccccc");
        }
        // Launch!
        startActivityForResult(intent, 1);
	}
	/**
	 * 显示AlertDialog
	 */
	private void alertDialogShow() {
		new AlertDialog.Builder(getActivity()).setTitle("清除？").setMessage("是否清除计数？\n注：该操作不可逆！").setPositiveButton("清除", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
				SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("TomatoCount",
						Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				editor.putInt("todayTomatoCount", 0);
				editor.putInt("allTomatoCount", 0);
				editor.commit();
				Toast.makeText(getActivity(), "清除成功！", Toast.LENGTH_SHORT).show();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO 自动生成的方法存根
			}
		}).create().show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());  
        SharedPreferences.Editor editor = sharedPreferences.edit();  
		if (resultCode != getActivity().RESULT_OK) {
            return;
        }
		
		switch (requestCode) {
	      case 1:{
	    	  Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);//系统默认的铃声
	    	  if(null == pickedUri){
	    		  editor.putString(RINGTONE_TITLE_NAME, "静音");
	    		  editor.putString(RINGTONE, null); 
	    		  editor.commit(); 
	    	  }else{
	    		  Ringtone ringtone =  RingtoneManager.getRingtone(getActivity(), pickedUri);
	    		  String strRingtone = ringtone.getTitle(getActivity());
	    		  editor.putString(RINGTONE_TITLE_NAME, strRingtone);
	    		  editor.putString(RINGTONE, pickedUri.toString());  
	    		  editor.commit();  
	    	  }
	    	  break;
	      }
	      default:break;
	}

		mSoundsPref.setSummary(sharedPreferences.getString(RINGTONE_TITLE_NAME, null));
		super.onActivityResult(requestCode, resultCode, data);
	}
	

}
