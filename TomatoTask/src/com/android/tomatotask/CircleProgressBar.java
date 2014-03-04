package com.android.tomatotask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleProgressBar extends View {
	private final String TAG = "CircleProgressBar";
	private int maxProgress = 10;//最大进度
	private int progress = 0;//当前进度
	private int progressStrokeWidth = 6;//线宽
	// 画圆所在的矩形区域
	RectF oval;
	Paint paint;

	public CircleProgressBar(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	public CircleProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		oval = new RectF();
		paint = new Paint();
	}

	public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		/**
		 * 画最外层的大圆环
		 */
		int centre = getWidth()/2; //获取圆心的x坐标
		int radius = (int) (centre - progressStrokeWidth/2); //圆环的半径
		paint.setColor(Color.WHITE);//(roundColor); //设置圆环的颜色
		paint.setStyle(Paint.Style.STROKE); //设置空心
		paint.setStrokeWidth(progressStrokeWidth); //设置圆环的宽度
		paint.setAntiAlias(true);  //消除锯齿 
		canvas.drawCircle(centre, centre, radius, paint); //画出圆环
		
		/**
		 * 画圆弧 ，画圆环的进度
		 */
		
		//设置进度是实心还是空心
		paint.setStrokeWidth(progressStrokeWidth); //设置圆环的宽度
		paint.setColor(Color.rgb(0x57, 0x87, 0xb6));  //设置进度的颜色 
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);  //用于定义的圆弧的形状和大小的界限

			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, -90, 360 * progress / maxProgress, false, paint);  //根据进度画圆弧  绘制白色圆圈，即进度条背景
	}
	
	public int getMaxProgress(){
		return maxProgress;
	}
	
	public void setMaxProgress(int maxProgress){
		this.maxProgress = maxProgress;
	}
	
	public void setProgress(int progress){
		this.progress = progress;
		this.invalidate();
	}
	
	public void setProgressNotInUiThread(int progress){
		this.progress = progress;
		this.postInvalidate();
	}

}




























