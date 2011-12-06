package com.pshegger.test.camera;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class CameraOverlay extends View {
	private Display display;
	private Context ctx;
	private float direction;
	public static SensorManager sensorMan;
	private int[] menuItems = {R.drawable.ic_launcher, R.drawable.android, R.drawable.chrome, R.drawable.gear, R.drawable.ubuntu};
	private Vector<DisplayObject> objects;
	private int maxWidth;
	private int currentImg;
	
	public CameraOverlay(Context context) {
		super(context);
		ctx = context;
		display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		direction = 0;
		sensorMan = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
		sensorMan.registerListener(listener, sensorMan.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
		objects = new Vector<DisplayObject>();
		maxWidth = 0;
		currentImg = R.drawable.ic_launcher;
		
		// Add as many objects as you want
		objects.addElement(new DisplayObject(ctx, R.drawable.ic_launcher, 42));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return false;
		
		int new_y = 10;
		for (int i=0; i<menuItems.length; i++) {
			Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), menuItems[i]);
			if (event.getX() < bmp.getWidth() && event.getY() > new_y && event.getY() < new_y+bmp.getHeight()) {
				currentImg = menuItems[i];
			}
			new_y += bmp.getHeight();
		}
		
		objects.addElement(new DisplayObject(ctx, currentImg, Math.round(direction)));
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = display.getWidth();
		int height = display.getHeight();
		
		Paint p = new Paint();
		
		// Draw Objects
		for (int i=0; i<objects.size(); i++) {
			DisplayObject obj = (DisplayObject) objects.elementAt(i);
			obj.drawObject(canvas, direction);
		}
		
		// Draw the menu
		p.setARGB(200, 150, 150, 150);
		canvas.drawRect(0, 0, maxWidth+10, height, p);
		int new_y = 10;
		for (int i=0; i<menuItems.length; i++) {
			Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), menuItems[i]);
			canvas.drawBitmap(bmp, 5, new_y, p);
			new_y += bmp.getHeight();
			if (bmp.getWidth() > maxWidth)
				maxWidth = bmp.getWidth();
		}
		
		p.setARGB(255, 255, 0, 0);
		p.setStrokeWidth(5);
		canvas.drawLine(width/2, 0, width/2, height, p);
		
		p.setARGB(255, 255, 255, 255);
		canvas.drawRect(width/2-50, height-50, width/2+50, height, p);
		p.setARGB(255, 0, 0, 0);
		p.setTextSize(30.0f);
		p.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(Integer.toString(Math.round(direction))+"°", width/2, height-12, p);
		invalidate();
	}
	
	SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float vals[] = event.values;
			direction = vals[0];
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
}
