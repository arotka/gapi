package com.rotka.utils;

import java.io.File;
import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;

public class TwitterHandler 
{
  private static Twitter twitter; 
  
  public static void sendTweet(String imgPath) throws TwitterException
  {
    twitter = setupTwitter();
    if (twitter != null) {
      try {
        twitter.updateStatus(" Do you have a visit : " + new Date() + "\n " + uploadTwicPic(imgPath));
      } catch (TwitterException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  private static Twitter setupTwitter() throws TwitterException {
    if (GapiConfiguration.TWITTER_CONFIGURED) {
      TwitterFactory factory = new TwitterFactory();
      final Twitter twitter = factory.getInstance();
      AccessToken accessToken = loadAccessToken();
      authenticateTwitter(accessToken, twitter);
      return twitter;
    }
    return null;
  }
  
  private static AccessToken loadAccessToken() {
    String token = GapiConfiguration.token;
    String tokenSecret = GapiConfiguration.secret;
    return new AccessToken(token, tokenSecret);
  }
  
  private static void authenticateTwitter(AccessToken accessToken, Twitter twitter) {
    twitter.setOAuthConsumer(GapiConfiguration.APP_KEY, GapiConfiguration.APP_SECRET);
    twitter.setOAuthAccessToken(accessToken);
  }

  private static String uploadTwicPic(String imgPath) throws TwitterException {
    java.io.File file = new File(imgPath);
    String url = null;
    if (file != null) {
      Configuration conf = new ConfigurationBuilder().setMediaProviderAPIKey(GapiConfiguration.APP_KEY).build();
      ImageUpload upload = new ImageUploadFactory(conf).getInstance(MediaProvider.TWITPIC); //Use ImageUploadFactory
      url = upload.upload(file);
    }
    return url;
  }
}