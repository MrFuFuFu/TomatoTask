package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BreakActivity extends Activity implements OnClickListener {
	private TextView breakTextView;
	private ProgressBar breakProgressBar;
	private Button returnbButton;
	private int rest=0;
	private TimeCount time;
	private int timeSpan;
	private long exitTime = 0;

	private boolean showShake = true;// 震动
	private boolean showTick = true;// 滴答声
	private Vibrator vibrator;

	public BreakActivity() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.break_layout);
		Log.v("MAIN", "-------BreakActivity---------onCreate-------");
		breakProgressBar = (ProgressBar)findViewById(R.id.breakBar);
		breakTextView = (TextView)findViewById(R.id.breakTxtView);
		returnbButton = (Button)findViewById(R.id.ReturnButton);
		//修改字体
		Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		breakTextView.setTypeface(fontFace);
//		returnbButton.setTypeface(fontFace);
		Intent intent = getIntent();
		rest = intent.getIntExtra("rest", 5);
		showShake=intent.getBooleanExtra("showShake", true);
		showTick=intent.getBooleanExtra("showTick", true);
		timeSpan = rest * 60 * 1000;
		breakProgressBar.setMax(rest*60);
		returnbButton.setOnClickListener(this);
		time = new TimeCount(timeSpan, 1000);// 构造CountDownTimer对象
		time.start();
		
	}
	
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}
		/**
		 * 计时过程显示
		 */
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO 自动生成的方法存根
			int nowprogress;
			String string = new SimpleDateFormat("mm:ss").format(new Date(
					millisUntilFinished));
			breakTextView.setText(string);
			nowprogress = (int)(rest*60-millisUntilFinished/1000);
			breakProgressBar.setProgress(nowprogress);
		}
		/**
		 * 计时完毕时触发
		 */
		@Override
		public void onFinish() {
			// TODO 自动生成的方法存根
			if (showShake) {
				//开启震动
				vibrator =(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
				long [] pattern = {200,500,200,500,1200,500,200,500};   // 停止 开启 停止 开启  
				vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1   
			}
			returnbButton.setVisibility(View.VISIBLE);
		}

		
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		finish();//关闭当前activity 进入上一个activity该activity必须是主activity
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
					BreakActivity.this);
			alertBuilder
					.setTitle("番茄？")
					.setMessage("放弃休息继续下一个番茄？")
					.setPositiveButton("番茄",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO 自动生成的方法存根
									time.cancel();
									BreakActivity.this.finish();
								}
							})
					.setNegativeButton("休息",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO 自动生成的方法存根
									dialog.cancel();
								}
							}).create();
			alertBuilder.show();
		}
	    return super.onKeyDown(keyCode, event);
	}

	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.v("MAIN", "-------BreakActivity---------onRestart-------");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("MAIN", "-------BreakActivity---------onResume-------");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("MAIN", "-------BreakActivity---------onStop-------");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("MAIN", "-------BreakActivity---------onPause-------");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("MAIN", "-------BreakActivity---------onDestroy-------");
	}
}
