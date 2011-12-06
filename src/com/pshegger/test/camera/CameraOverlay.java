package com.pshegger.test.camera;

import java.util.Vector;

import android.content.Context;
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
	private Vector<DisplayObject> objects;
	
	public CameraOverlay(Context context) {
		super(context);
		ctx = context;
		display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		direction = 0;
		sensorMan = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
		sensorMan.registerListener(listener, sensorMan.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
		objects = new Vector<DisplayObject>();
		
		// Add as many objects as you want
		objects.addElement(new DisplayObject(ctx, R.drawable.ic_launcher, 42));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		objects.addElement(new DisplayObject(ctx, R.drawable.ic_launcher, Math.round(direction)));
		return super.onTouchEvent(event);
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
