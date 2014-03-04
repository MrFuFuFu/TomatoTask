package com.android.tomatotask.Task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class DrawLine extends EditText {
	private Paint ePaint;

	// private Rect rect;
	public DrawLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		ePaint = new Paint();

		// rect=new Rect();
		ePaint.setColor(Color.BLACK);
		ePaint.setStyle(Paint.Style.STROKE);

	}

	// 标记3，此处线字交叉是由于行间距引起,注意使用getLineHeight()得到行高。

	public void onDraw(Canvas canvas) {
		int count = getLineCount();
		for (int i = 0; i < count + 11; i++) {
			float[] pts = { 15.0F, (i + 1) * getLineHeight(),
					this.getWidth() - 20.0F, (i + 1) * getLineHeight() };

			// i*50-280,50增加则行间距大285增加则线靠上。
			// canvas.drawLine(15, i*42, this.getWidth()-20,i*42, ePaint);
			canvas.drawLines(pts, ePaint);
		}
		super.onDraw(canvas);
	}

}
