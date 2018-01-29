package org.wimidev.airhockey;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRenderer implements Renderer {
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	
	public AirHockeyRenderer()
	{
		float[] tableVertices = {
				0f,0f,
				0f,14f,
				9f,14f,
				9f,0f
		};
		float[] tableVerticesWithTriangles = {
			//t1
			0f,0f,
			9f,14f,
			0f,14f,
			
			//t2
			0f,0f,
			9f,0f,
			9f,14f,
			
			//line1
			0f,7f,
			9f,7f,
			
			//mallets
			4.5f,2f,
			4.5f,12f
		};
		
	vertexData = ByteBuffer
			        .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
			        .order(ByteOrder.nativeOrder())
			        .asFloatBuffer();

	vertexData.put(tableVerticesWithTriangles);

	}
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
	{
		glClearColor(1.0f,0.0f,0.0f,0.0f);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);
	}
}
