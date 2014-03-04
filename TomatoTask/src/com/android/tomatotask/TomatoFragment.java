package com.android.tomatotask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TomatoFragment extends Fragment{
//	private CircleProgressBar tomatoProgressBar;
	private TextView tomatoTxtView,todayTomatoCountTextView,allTomatoCountTextView;
	private ImageView imageView;
	private View mMainView;
	private int todayTomatoCount;
	private int allTomatoCount;

	public TomatoFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.tomato_layout, (ViewGroup)getActivity().findViewById(R.id.viewpager),false);

		tomatoTxtView = (TextView)mMainView.findViewById(R.id.tomatoTxtView);
		todayTomatoCountTextView = (TextView)mMainView.findViewById(R.id.todayTomatoCount);
		allTomatoCountTextView = (TextView)mMainView.findViewById(R.id.allTomatoCount);
		imageView =  (ImageView)mMainView.findViewById(R.id.imageTomato);
		//修改字体
		Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
		tomatoTxtView.setTypeface(fontFace);
		todayTomatoCountTextView.setTypeface(fontFace);
		allTomatoCountTextView.setTypeface(fontFace);
		

		Log.i("MAIN", "++++++++TomatoFragment++++++++onCreate++++++++");
		imageView.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				
				// TODO 自动生成的方法存根
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO 自动生成的方法存根
		Log.i("MAIN", "++++++++TomatoFragment++++++++onCreateView++++++++");
		ViewGroup p = (ViewGroup)mMainView.getParent();
		if (p!=null) {
			p.removeAllViewsInLayout();
			Log.i("MAIN", "++++++++TomatoFragment++++++++removeAllViewsInLayout!!!!!!!!");
		}
			return mMainView;
		}
	
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		Log.i("MAIN", "++++++++TomatoFragment++++++++onResume++++++++");
		//时间日期的读取，如果时间不是今天的时间，则置今日番茄数为 0 
		// 配置读取
		SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("TomatoCount",
				Activity.MODE_PRIVATE);
		String dateStr = mySharedPreferences.getString("date", "2001-01-01");
		todayTomatoCount = mySharedPreferences.getInt("todayTomatoCount", 0);//获取存储的今日番茄时间
		allTomatoCount = mySharedPreferences.getInt("allTomatoCount", 0);//获取存储的合计番茄时间
		String dateNowString = (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).format(new java.util.Date());
		if (!dateStr.equals(dateNowString)) {//判断存储时间是否和当前时间在同一天
			todayTomatoCount=0;
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putInt("todayTomatoCount", todayTomatoCount);
			editor.commit();
		}
		todayTomatoCountTextView.setText("今日:"+todayTomatoCount);
		allTomatoCountTextView.setText("总计:"+allTomatoCount);
		Log.v("MAIN", "----TomatoFragment---todayTomatoCount---------"+todayTomatoCount+"-------");
		Log.v("MAIN", "----TomatoFragment---allTomatoCount---------"+allTomatoCount+"-------");
		Log.v("MAIN", "----TomatoFragment---dateStr---------"+dateStr+"-------");
		
	}
	
	
	@Override
		public void onDestroy() {
			// TODO 自动生成的方法存根
			super.onDestroy();
			Log.i("MAIN", "++++++++TomatoFragment++++++++onDestroy++++++++");
		}
	@Override
		public void onPause() {
			// TODO 自动生成的方法存根
			super.onPause();
			Log.i("MAIN", "++++++++TomatoFragment++++++++onPause++++++++");
		}

	
	@Override
		public void onStart() {
			// TODO 自动生成的方法存根
			super.onStart();
			Log.i("MAIN", "++++++++TomatoFragment++++++++onStart++++++++");
		}
	
	@Override
		public void onStop() {
			// TODO 自动生成的方法存根
			super.onStop();
			Log.i("MAIN", "++++++++TomatoFragment++++++++onStop++++++++");
		}

}
