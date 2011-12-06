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
		int deltaPix = Math.round(Math.abs(direction-this.pos)*pixelsPerAngle);
		if (direction > this.pos)
			deltaPix *= -1;
		
		c.drawBitmap(this.img, display.getWidth()/2+deltaPix, display.getHeight()/2-img.getHeight()/2, p);
	}
}
