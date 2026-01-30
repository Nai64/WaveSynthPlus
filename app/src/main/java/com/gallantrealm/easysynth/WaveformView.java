package com.gallantrealm.easysynth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class WaveformView extends View {
	
	private Paint paint;
	private Paint gridPaint;
	private Path path;
	private float[] audioData;
	private int dataIndex = 0;
	private static final int MAX_POINTS = 512;
	
	public WaveformView(Context context) {
		super(context);
		init();
	}
	
	public WaveformView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		paint = new Paint();
		paint.setColor(0xFF00FF00); // Green waveform
		paint.setStrokeWidth(2f);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		
		gridPaint = new Paint();
		gridPaint.setColor(0xFF333333); // Dark gray grid
		gridPaint.setStrokeWidth(1f);
		gridPaint.setStyle(Paint.Style.STROKE);
		gridPaint.setAntiAlias(true);
		
		path = new Path();
		audioData = new float[MAX_POINTS];
	}
	
	public void updateAudioData(short[] buffer, int length) {
		if (buffer == null || length == 0) return;
		
		// Downsample buffer to fit MAX_POINTS
		int step = Math.max(1, length / MAX_POINTS);
		int index = 0;
		
		for (int i = 0; i < length && index < MAX_POINTS; i += step) {
			audioData[index++] = buffer[i] / 32768.0f; // Normalize to -1.0 to 1.0
		}
		dataIndex = index;
		
		postInvalidate(); // Trigger redraw
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int width = getWidth();
		int height = getHeight();
		int centerY = height / 2;
		
		// Draw grid
		// Horizontal center line
		canvas.drawLine(0, centerY, width, centerY, gridPaint);
		
		// Horizontal lines
		for (int i = 1; i <= 4; i++) {
			float y = (height * i) / 5;
			canvas.drawLine(0, y, width, y, gridPaint);
		}
		
		// Vertical lines
		for (int i = 1; i <= 10; i++) {
			float x = (width * i) / 11;
			canvas.drawLine(x, 0, x, height, gridPaint);
		}
		
		// Draw waveform
		if (dataIndex > 1) {
			path.reset();
			
			float xScale = (float) width / (dataIndex - 1);
			float yScale = (height / 2) * 0.9f; // Leave 10% margin
			
			// Start path
			path.moveTo(0, centerY - (audioData[0] * yScale));
			
			// Draw waveform
			for (int i = 1; i < dataIndex; i++) {
				float x = i * xScale;
				float y = centerY - (audioData[i] * yScale);
				path.lineTo(x, y);
			}
			
			canvas.drawPath(path, paint);
		}
	}
}

