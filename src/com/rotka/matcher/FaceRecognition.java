package com.rotka.matcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import com.rotka.utils.GapiConfiguration;

public class FaceRecognition 
{	
	final static String HIT = "HIT";
	final static String NO_HIT = "NO_HIT";
	
	public static boolean performMatching(String imgPath) throws IOException 
	{
		String result = NO_HIT;
		String strCmd = "python " + GapiConfiguration.MATCHER_SCRIPT + " " + imgPath;
		Process p = Runtime.getRuntime().exec(strCmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		result = in.readLine();
		System.out.println(imgPath +  " => " +result);
		if(result != null && result.equals(HIT))
			return true;
		
		return false;
	}
	
}