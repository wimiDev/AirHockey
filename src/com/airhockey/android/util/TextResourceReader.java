package com.airhockey.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.Resources;

public class TextResourceReader {
	public static String readTextFileFromeResourse(Context context,int resourceid)
	{
		StringBuilder body = new StringBuilder();
		try{
			InputStream inputStream = context.getResources().openRawResource(resourceid);
			
			InputStreamReader inputStreamReader = 
				new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String nextLine;
			
			while((nextLine = bufferedReader.readLine()) != null )
			{
				body.append(nextLine);
				body.append("\n");
			}
		}
		
		catch (IOException e)
		{
			throw new RuntimeException("Could not open resource: " + resourceid, e);
		}
		
		catch (Resources.NotFoundException nfe)
		{
			throw new RuntimeException("Resource not found: " + resourceid, nfe);
		}
		
		return body.toString() ;
	}
}
