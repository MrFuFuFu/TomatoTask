package com.android.tomatotask;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

	private SeekBar sbTick;
	private SeekBar sbRest;
	private SeekBar sbLongRest;

	private TextView tvTick;
	private TextView tvRest;
	private TextView tvLongRest;

	private CheckBox chkShake;
	private CheckBox chkTick;

	private View mMainView;

	private SharedPreferences mySharedPreferences;
	private int tick, rest, longrest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.setting_layout,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);
		Log.i("MAIN", "********SettingFragment********onCreate********");

		chkShake = (CheckBox) mMainView.findViewById(R.id.chkShake);
		chkTick = (CheckBox) mMainView.findViewById(R.id.chkTick);

		// 三个时间设置
		sbTick = (SeekBar) mMainView.findViewById(R.id.sbTick);
		sbRest = (SeekBar) mMainView.findViewById(R.id.sbRest);
		sbLongRest = (SeekBar) mMainView.findViewById(R.id.sbLongRest);

		tvTick = (TextView) mMainView.findViewById(R.id.tvTick);
		tvRest = (TextView) mMainView.findViewById(R.id.tvRest);
		tvLongRest = (TextView) mMainView.findViewById(R.id.tvLongRest);

		sbTick.setOnSeekBarChangeListener(new SeekBarListener(tvTick, 20, 5));
		sbRest.setOnSeekBarChangeListener(new SeekBarListener(tvRest, 1, 1));
		sbLongRest.setOnSeekBarChangeListener(new SeekBarListener(tvLongRest,
				5, 5));
//		// 修改字体
//		Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(),
//				"fonts/Roboto-Thin.ttf");
//		tvTick.setTypeface(fontFace);
//		tvRest.setTypeface(fontFace);
//		tvLongRest.setTypeface(fontFace);
//		chkShake.setTypeface(fontFace);
//		chkTick.setTypeface(fontFace);

		// 配置读取
		mySharedPreferences = getActivity().getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		// process=（时间-初始值）/步长
		tick = (mySharedPreferences.getInt("tick", 20) - 20);
		rest = mySharedPreferences.getInt("rest", 1) - 1;
		longrest = (mySharedPreferences.getInt("longrest", 5) - 5);
		Log.i("MAIN", "********tick="+tick+"****rest="+rest+"****longrest="+longrest+"********");

		// 这三行代码只是为了触发监听事件
		sbTick.setProgress(tick + 1);
		sbRest.setProgress(rest + 1);
		sbLongRest.setProgress(longrest + 1);

		sbTick.setProgress(tick);
		sbRest.setProgress(rest);
		sbLongRest.setProgress(longrest);
		// tvTick.setText(tick+20+"min");
		// tvRest.setText(rest+1+"min");
		// tvLongRest.setText(longrest+5+"min");

		// 震动和声音设置
		boolean showShake = mySharedPreferences.getBoolean("showshake", true);
		boolean showTick = mySharedPreferences.getBoolean("showtick", true);

		chkShake.setChecked(showShake);
		chkTick.setChecked(showTick);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		Log.i("MAIN", "********SettingFragment********onCreateView********");
		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
			Log.i("MAIN",
					"********SettingFragment********removeAllViewsInLayout!!!!!!!!");
		}
		return mMainView;
	}

	@Override
	public void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();
		Log.i("MAIN", "********SettingFragment********onStop********");

		// 计算公式：时间=process*步长+初始值
		int tick_Change = sbTick.getProgress() + 20;
		int rest_Change = sbRest.getProgress() + 1;
		int longrest_Change = sbLongRest.getProgress() + 5;

		boolean showShake = chkShake.isChecked();
		boolean showTick = chkTick.isChecked();
		Log.i("MAIN", "****tick="+tick+"****tick_Change = "+tick_Change+"****rest="+rest +" ****rest_Change="+rest_Change+"***longrest="+longrest+"***longrest_Change="+longrest_Change+"**");
		if (tick+20 == tick_Change && rest+1 == rest_Change
				&& longrest+5 == longrest_Change)
			return;

		// 如果设置有改变，则做如下操作
		
		// 实例化SharedPreferences对象（第一步）
		mySharedPreferences = getActivity().getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		// 实例化SharedPreferences.Editor对象（第二步）
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		// 用putString的方法保存数据
		editor.putInt("tick", tick_Change);
		editor.putInt("rest", rest_Change);
		editor.putInt("longrest", longrest_Change);
		editor.putBoolean("showshake", showShake);
		editor.putBoolean("showtick", showTick);

		// 提交当前数据
		editor.commit();

		// 提示已保存
		Toast.makeText(getActivity(), "设置已保存！", Toast.LENGTH_SHORT).show();
		Log.i("MAIN", "********SettingFragment********设置已保存！********");
		// finish();
	}

	private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
		private TextView textView;
		private int TickStep;
		private int StartTick;

		public SeekBarListener(TextView tv, int startTick, int tickStep) {
			textView = tv;
			TickStep = tickStep;
			StartTick = startTick;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			if (fromUser) {
				// ..
			}
			// 时间=process*步长+初始值
			// int progress=seekBar.getProgress();
			int curTick = progress + StartTick;
			int remainder = curTick % TickStep;
			int halfStep = TickStep % 2 == 0 ? TickStep - TickStep % 2
					: TickStep - TickStep % 2 + 1;

			if (remainder < halfStep) {
				curTick -= remainder;
			} else {
				curTick += (TickStep - remainder);
			}
			// seekBar.setProgress(curTick - StartTick);
			textView.setText(curTick + "min");
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			// 时间=process*步长+初始值
			int progress = seekBar.getProgress();
			int curTick = progress + StartTick;
			int remainder = curTick % TickStep;
			int halfStep = TickStep % 2 == 0 ? TickStep - TickStep % 2
					: TickStep - TickStep % 2 + 1;

			if (remainder < halfStep) {
				curTick -= remainder;
			} else {
				curTick += (TickStep - remainder);
			}
			seekBar.setProgress(curTick - StartTick);
			textView.setText(curTick + "min");
		}

	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		Log.i("MAIN", "********SettingFragment********onDestroy********");
	}

	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		Log.i("MAIN", "********SettingFragment********onPause********");
	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		Log.i("MAIN", "********SettingFragment********onResume********");
	}

	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		Log.i("MAIN", "********SettingFragment********onStart********");
	}
}
