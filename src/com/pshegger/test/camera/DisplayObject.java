package com.pshegger.test.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.WindowManager;

public class DisplayObject {
	private int _id;
	private int pos;
	private Bitmap img;
	private Context ctx;
	private Display display;
	
	public DisplayObject(Context context, int img_id, int position) {
		this.pos = position;
		this._id = img_id;
		this.ctx = context;
		this.img = BitmapFactory.decodeResource(this.ctx.getResources(), this._id);
		this.display = ((WindowManager) this.ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	}
	
	public void drawObject(Canvas c, float direction) {
		Paint p = new Paint();
		float pixelsPerAngle = this.display.getWidth()/50;
		float angle = (float) (Math.atan2(Math.sin(Math.toRadians(this.pos)), Math.cos(Math.toRadians(this.pos))) - Math.atan2(Math.sin(Math.toRadians(direction)), Math.cos(Math.toRadians(direction))));
		int deltaPix = Math.round((float) Math.toDegrees(angle)*pixelsPerAngle);
		
		c.drawBitmap(this.img, display.getWidth()/2+deltaPix-img.getWidth()/2, display.getHeight()/2-img.getHeight()/2, p);
	}
}
