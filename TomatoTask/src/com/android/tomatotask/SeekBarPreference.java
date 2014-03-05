package com.android.tomatotask;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class SeekBarPreference extends DialogPreference {
	private Context context;
	private SeekBar sensitivityLevel=null;
	private LinearLayout layout=null;

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		persistInt(10);
	}
	public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		// TODO Auto-generated method stub
//		super.onPrepareDialogBuilder(builder);
		
		//添加布局
		layout = new LinearLayout(context);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));//布局属性
		layout.setMinimumWidth(400);//布局的最小宽度
		layout.setPadding(20, 20, 20, 20);//上下左右的padding
		
		//添加seekbar
		sensitivityLevel = new SeekBar(context);
		sensitivityLevel.setMax(100);//最大值
		sensitivityLevel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));//SeekBar的布局属性
		
		sensitivityLevel.setProgress(getPersistedInt(10));//设置默认值
		layout.addView(sensitivityLevel);//把seekbar添加到layout布局中
		builder.setView(layout);
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		if (positiveResult) {
			persistInt(sensitivityLevel.getProgress());//保存SeekBar的值
		}
		
		super.onDialogClosed(positiveResult);
	}

}











