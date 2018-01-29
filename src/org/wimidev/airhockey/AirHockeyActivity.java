package org.wimidev.airhockey;

import org.wimidev.airhockey.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AirHockeyActivity extends Activity {
	
	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		glSurfaceView = new GLSurfaceView(this);//初始化gl
		/////检查执行环境
		final ActivityManager acManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo cfg = acManager.getDeviceConfigurationInfo();
		final boolean supportsEs2 = cfg.reqGlEsVersion >= 0x20000
				||(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
				||(Build.FINGERPRINT.startsWith("generic"))
				|| (Build.FINGERPRINT.startsWith("unknow"))
				|| Build.MODEL.contains("google_sdk")
				|| Build.MODEL.contains("Emulator")
				|| Build.MODEL.contains("Android SDK built for x86")
				;
		//////配置渲染表面
		if (supportsEs2)
		{
			glSurfaceView.setEGLContextClientVersion(2);
			glSurfaceView.setRenderer(new AirHockeyRenderer());
			rendererSet = true;
		}
		else
		{
			Toast.makeText(this, "this device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG).show();
		}
		
		
		setContentView(glSurfaceView);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		if(rendererSet)
		{
			glSurfaceView.onPause();
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(rendererSet)
		{
			glSurfaceView.onResume();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_gl, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
