package com.airhockey.android.util;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

import android.util.Log;

public class ShaderHelper {
	private static final String TAG = "ShaderHelper";
	
	public static int compileVertexShader(String shaderCode)
	{
		return compilpShader(GL_VERTEX_SHADER, shaderCode);
	}
	
	public static int compileFragmentShader(String shaderCode)
	{
		return compilpShader(GL_FRAGMENT_SHADER, shaderCode);
	}
	
	public static int compilpShader(int type, String ShaderCode)
	{
		final int shaderObjectId = glCreateShader(type); //创建着色器 create shader
		if (shaderObjectId == 0)
		{
			if (LoggerConfig.ON){
				Log.w(TAG, "Could not create new shader.");
			}
			return 0;
		}
		
		//把源码上传至着色器 upload source to shader
		glShaderSource(shaderObjectId, ShaderCode);
		//编译着色器 Compile Shader
		glCompileShader(shaderObjectId);
		
		//获取编译状态
		final int[] compileStates = new int[1];
		glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStates, 0);
		if (LoggerConfig.ON)
		{
			Log.v(TAG, "Results of compile source: " + "\n" + ShaderCode + "\n"
					+ glGetShaderInfoLog(shaderObjectId));
		}
		
		if(compileStates[0] == 0)
		{
			glDeleteShader(shaderObjectId);
			if(LoggerConfig.ON)
			{
				Log.w(TAG, "compilation of shader failed.");
			}
			return 0;
		}
		return shaderObjectId;
	}
	
	public static int linkProgram(int vertexShaderId, int fragmentShaderId)
	{
		//create a Program
		final int programObjectId = glCreateProgram();
		
		if (programObjectId == 0 )
		{
			if(LoggerConfig.ON)
			{
				Log.w(TAG, " Could not create new program");
			}
			return 0;
		}
		
		//Attach  shader
		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
		
		//Linking Program
		glLinkProgram(programObjectId);  
		final int[] linkStatus = new int[1];
		glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
		if(LoggerConfig.ON)
		{
			Log.v(TAG, "Results of linking program:/n"+
						glGetProgramInfoLog(programObjectId));
		}
		
		if(linkStatus[0] == 0)
		{
			glDeleteProgram(programObjectId);
			if(LoggerConfig.ON)
			{
				Log.w(TAG, "Linking of program failed");
			}
			return 0;
		}
		return programObjectId;
	}
	
	public static boolean validateProgram(int programObjectId)
	{
		glValidateProgram(programObjectId);
		final int[] validateStatus = new int[1];
		glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
		Log.v(TAG, "Results of validating program " + validateStatus[0]
				+ "\n Log: " + glGetProgramInfoLog(programObjectId));
		
		return false;
	}
}
