package com.pshegger.test.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomeCameraView extends SurfaceView {
	Camera camera;
	SurfaceHolder previewHolder;
	
	SurfaceHolder.Callback surfaceHolderListener = new SurfaceHolder.Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			camera.stopPreview();
			camera.release();
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			camera = Camera.open();
			try {	
				camera.setPreviewDisplay(previewHolder);
			} catch(Throwable t) {}
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Parameters params = camera.getParameters();
			params.setPreviewSize(width, height);
			params.setPictureFormat(PixelFormat.JPEG);
			camera.setParameters(params);
			camera.startPreview();
		}
	};
	
	public CustomeCameraView(Context ctx) {
		super(ctx);
		
		previewHolder = this.getHolder();
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		previewHolder.addCallback(surfaceHolderListener);
	}
	
	
}