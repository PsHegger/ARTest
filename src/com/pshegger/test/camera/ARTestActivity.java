package com.pshegger.test.camera;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class ARTestActivity extends Activity {
	private CustomeCameraView cv;
	private CameraOverlay co;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
        	super.onCreate(savedInstanceState);
        	cv = new CustomeCameraView(getApplicationContext());
        	co = new CameraOverlay(getApplicationContext());
        	RelativeLayout r1 = new RelativeLayout(getApplicationContext());
        	setContentView(r1);
        	r1.addView(cv);
        	r1.addView(co);
        	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch(Exception e) {}
    }
}