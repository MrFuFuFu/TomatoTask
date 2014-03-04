package com.android.tomatotask.Task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewLine extends TextView {

	private Paint ePaint = new Paint();

	public TextViewLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		this.ePaint.setColor(-16777216);
		this.ePaint.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		int i=getLineCount();
	    for (int j = 0; ; ++j)
	    {
	      if (j >= i)
	      {
	        super.onDraw(canvas);
	        return;
	      }
	      float[] arrayOfFloat = new float[4];
	      arrayOfFloat[0] = 15.0F;
	      arrayOfFloat[1] = ((j + 1) * getLineHeight());
	      arrayOfFloat[2] = (-20 + getWidth());
	      arrayOfFloat[3] = ((j + 1) * getLineHeight());
	      canvas.drawLines(arrayOfFloat, this.ePaint);
	    }
	}

}
