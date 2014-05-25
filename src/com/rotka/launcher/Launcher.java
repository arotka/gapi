package com.rotka.launcher;

import java.io.IOException;

import twitter4j.TwitterException;

import com.rotka.gateway.GatewayAccess;
import com.rotka.matcher.FaceRecognition;
//import com.rotka.matcher.FaceDetector;
import com.rotka.utils.GapiConfiguration;
import com.rotka.utils.TwitterHandler;
import com.rotka.watcher.IMotionEvent;
import com.rotka.watcher.MotionWatcher;


public class Launcher implements IMotionEvent
{
  
	public void startApp()
	{
	  System.out.println("--> Start watching motion folder "+GapiConfiguration.DIRECTORY_TO_WATCH);
		MotionWatcher motionWatcher = new MotionWatcher(GapiConfiguration.DIRECTORY_TO_WATCH, this);
		motionWatcher.startWatching();	
	}
	
	public void stopApp()
	{
	  System.out.println("--> Stop gapi");
	}
    
	@Override
	public void motionDetected(String path)
	{
		boolean isHit = false;
		try {
		  isHit = FaceRecognition.performMatching(path);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		if(isHit)
		{
      System.out.println("--> The face has been recognized successfully : "+path);
		  GatewayAccess.unlockDoor();
		  try {
		    System.out.println("--> Send tweet");
        TwitterHandler.sendTweet(path);
      } catch (TwitterException e) {
        System.out.println("Error when trying to send a tweet : " +  e.getMessage());;
      }
		}
	}
	

}