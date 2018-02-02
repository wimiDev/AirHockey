package org.wimidev.airhockey;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.airhockey.android.util.LoggerConfig;
import com.airhockey.android.util.ShaderHelper;
import com.airhockey.android.util.TextResourceReader;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class AirHockeyRenderer implements Renderer {
	private static final int POSITION_COMPONENT_COUNT = 2;
	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	private final Context context;
	private int program;
	
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;
	 
	public AirHockeyRenderer(Context context)
	{
		this.context = context;
		float[] tableVertices = {
				0f,0f,
				0f,14f,
				9f,14f,
				9f,0f
		};
		float[] tableVerticesWithTriangles = {
			//t1
			-0.5f, -0.5f,
			0.5f, 0.5f,
			-0.5f, 0.5f,
			
			//t2
			-0.5f, -0.5f,
			0.5f, -0.5f,
			0.5f, 0.5f,
			
			//line1
			0.5f, 0f,
			0.5f, 0f,
			
			//mallets
			0f, -0.25f,
			0f, 0.25f
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
		glClearColor(0.0f,0.0f,0.0f,0.0f);
		
		//load shader code
		String vertexShaderSource = TextResourceReader
				.readTextFileFromeResourse(context, R.raw.simple_vertex_shader);
		String fragmentShaderSource =  TextResourceReader
				.readTextFileFromeResourse(context, R.raw.simple_fragment_shader);
		
		//complie shader
		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
		
		//linking shader
		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
		
		if(LoggerConfig.ON)
		{
			ShaderHelper.validateProgram(program);
		}
		
		//use it
		glUseProgram(program);
		
		//get unifrom location from link program
		uColorLocation = glGetUniformLocation(program, U_COLOR); 
		
		//get attrib from link program
		aPositionLocation = glGetAttribLocation(program, A_POSITION);
		
		//set Vertex data
		vertexData.position(0);
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
		
		//enable vertex attrib array
		glEnableVertexAttribArray(aPositionLocation);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
		
		//** draw a rect **
		//update ucolor data
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
		//draw form vertex array,draw tow triangles
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
		//** draw a line **
		glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
		glDrawArrays(GL_LINES, 6, 2);
		
		//** draw a chuizi **
		glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
		glDrawArrays(GL_POINTS, 8, 1);
		
		//** draw other chuizi **
		glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
		glDrawArrays(GL_POINTS, 9, 1);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);
	}
}
