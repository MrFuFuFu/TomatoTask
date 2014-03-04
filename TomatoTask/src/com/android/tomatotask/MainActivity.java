package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.android.tomatotask.R.string;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private CircleProgressBar progressBar;
	private TextView textView;
	private TimeCount time;
	protected Animation animation;
	private CharSequence tomatocharSequence;
	private CharSequence breakcharSequence;
	private int timeSpan;
	private final String TAG = "Main";
	private int flag = 1;// 标志位：1:番茄未开始，2:番茄开始后的，3:番茄时间到同时休息时间未开始，4:休息时间开始后的，1:休息时间到番茄未开始
	private int count = 0; // 统计任务数，考虑将统计的数据存到缓存当中去，同时记得写入文件。
	private int percentTime = 0;// 设置的秒数，用来计算圆圈的百分比数。
	private boolean flagHide = true;// true为显示数字时间，false为不显示数字时间
	private int tick = 0;
	private int rest = 0;
	private int longrest = 0;
	private int maxProgress;// 最大进度，该值与CircleProgressBar类中的maxProgress必须相同
	private long exitTime = 0;
	private int[] ID;
	
//	//ViewPager
//	LocalActivityManager manager =null;
//	ViewPager pager = null;
//	TabHost tabHost = null;
//	
//	private int offset = 0;// 动画图片偏移量
//	private int currIndex = 0;// 当前页卡编号
//	private int bmpW;// 动画图片宽度
//	private ImageView cursor;// 动画图片
//	private List<View> listViews; // Tab页面列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * 必须先在assets下创建fonts文件夹并放入字体文件，同时提供相对路径创建Typeface对象
		 * 当使用外部字体却又发现字体无变化时（以Droid Sans代替），通常是因为该字体android没有支持
		 */
		Log.v("MAIN", "-------MainActivity---------onCreate-------");
		Typeface fontFace = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		textView = (TextView) this.findViewById(R.id.txtView);
		textView.setTypeface(fontFace);
		progressBar = (CircleProgressBar) findViewById(R.id.circleProgressbar);
		tomatocharSequence = (CharSequence) (getResources()
				.getString(R.string.tomatocharSequence));
		breakcharSequence = (CharSequence) (getResources()
				.getString(R.string.breakcharSequence));
		// 配置读取
		SharedPreferences mySharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		tick = mySharedPreferences.getInt("tick", 25);
		tick = 1;//记得删除
		rest = mySharedPreferences.getInt("rest", 5);
		longrest = mySharedPreferences.getInt("longrest", 15);

		//设置progressBar最大进度
		progressBar.setMaxProgress(tick * 60);
		progressBar.setOnClickListener(this);

		//动画资源文件
		ID = new int[] { R.anim.my_alpha_action, R.anim.my_scale_action,
				R.anim.my_rotate_action, R.anim.alpha_scale,
				R.anim.alpha_rotate, R.anim.scale_rotate,
				R.anim.alpha_scale_rotate, R.anim.myown_design };
		
		
		stateFlag();

	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		/**
		 * 计时完毕时触发
		 */
		@Override
		public void onFinish() {
			// TODO 自动生成的方法存根
			switch (flag) {
			case 2:// 番茄开始后的，如果此时番茄时间到了，修改flag，提示时间到
				flag = 3;
				if (flagHide) {
					textView.setVisibility(View.VISIBLE);
					flagHide = false;
					// 增加动画效果
					int randow= new Random().nextInt(8);
					animation = AnimationUtils.loadAnimation(MainActivity.this, ID[randow]);
					textView.startAnimation(animation);
				}
				
				// 实例化SharedPreferences对象（第一步）
				SharedPreferences mySharedPreferences = getSharedPreferences("TomatoCount",
						Activity.MODE_PRIVATE);
				int todayTomatoCount;
				int allTomatoCount;
				todayTomatoCount = mySharedPreferences.getInt("todayTomatoCount", 0);
				allTomatoCount = mySharedPreferences.getInt("allTomatoCount", 0);
				String dateNowString = (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).format(new java.util.Date());//获取当前时间
				// 实例化SharedPreferences.Editor对象（第二步）
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				// 用putString的方法保存数据
				todayTomatoCount +=1;
				allTomatoCount+=1;
				editor.putInt("todayTomatoCount", todayTomatoCount);
				editor.putInt("allTomatoCount", allTomatoCount);
				editor.putString("date", dateNowString);
				// 提交当前数据
				editor.commit();
				Log.v("MAIN", "-------todayTomatoCount---------"+todayTomatoCount+"-------");
				Log.v("MAIN", "-------allTomatoCount---------"+allTomatoCount+"-------");
				Log.v("MAIN", "-------dateNowString---------"+dateNowString+"-------");
				
				
				Toast.makeText(MainActivity.this,
						getResources().getString(R.string.EndTask),
						Toast.LENGTH_SHORT).show();
				textView.setText("点此休息！");
				break;
			default:
				break;
			}
		}

		/**
		 * 计时过程显示
		 */
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO 自动生成的方法存根
			String string = new SimpleDateFormat("mm:ss").format(new Date(
					millisUntilFinished));
			textView.setText(string);
			progressBar
					.setProgressNotInUiThread((int) (millisUntilFinished / 1000));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			// Log.i(TAG, "onResume Method is executed");
			Intent intent = new Intent(MainActivity.this, SettingFragment.class);
			item.setIntent(intent);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (flag == 2) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(
						MainActivity.this);
				alertBuilder
						.setTitle("放弃？")
						.setMessage("是否放弃这个番茄并退出吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO 自动生成的方法存根
										MainActivity.this.finish();
									}
								})
						.setNegativeButton("取消",
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
		} else {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退到主界面",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
				}
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.v("MAIN", "-------MainActivity---------onRestart----Do Something----");
		SharedPreferences mySharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		tick = mySharedPreferences.getInt("tick", 25);
		rest = mySharedPreferences.getInt("rest", 5);
		longrest = mySharedPreferences.getInt("longrest", 15);
		progressBar.setMaxProgress(tick * 60);
		if (flag == 4) {
			flag = 1;
			if (flagHide) {
				textView.setVisibility(View.VISIBLE);
				flagHide = false;
				// 增加动画效果
				int randow= new Random().nextInt(8);
				animation = AnimationUtils.loadAnimation(MainActivity.this, ID[randow]);
				textView.startAnimation(animation);
			}
			stateFlag();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.circleProgressbar:
			stateFlag();

			if (flagHide) {
				textView.setVisibility(View.VISIBLE);
				flagHide = false;
				// 增加动画效果
				int randow= new Random().nextInt(8);
				animation = AnimationUtils.loadAnimation(this,ID[randow]);
				textView.startAnimation(animation);
			} else {
				textView.setVisibility(View.INVISIBLE);
				flagHide = true;
				// Log.i(TAG, "--flagHide-->>"+flagHide);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 番茄状态标志，如果是1则开始番茄，同时置flag为2；如果是3则番茄时间到，开始休息，flag置为4
	 */
	private void stateFlag() {
		switch (flag) {
		case 1:
			timeSpan = tick * 60 * 1000;
			percentTime = tick * 60;
			// timeSpan = 10 * 1000;
			// percentTime = 10;
			time = new TimeCount(timeSpan, 1000);// 构造CountDownTimer对象
			Toast.makeText(MainActivity.this, tomatocharSequence,
					Toast.LENGTH_SHORT).show();
			flag = 2;// 番茄开始后
			time.start();
			break;
		case 3://进入休息Activity
			Log.i(TAG, "--intent-->>" + rest);
			Intent intent = new Intent(getApplicationContext(),
					BreakActivity.class);
			intent.putExtra("rest", rest);
			flag = 4;
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("MAIN", "-------MainActivity---------onResume-------");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("MAIN", "-------MainActivity---------onStop-------");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("MAIN", "-------MainActivity---------onPause-------");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("MAIN", "-------MainActivity---------onDestroy-------");
	}
}
















