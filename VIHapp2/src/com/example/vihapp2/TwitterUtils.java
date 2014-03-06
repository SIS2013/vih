package com.example.vihapp2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import oauth.signpost.OAuth;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class TwitterUtils {

	public static boolean mandaTuit( String tuit, SharedPreferences prefs ){
		
    	AccessToken a = getAccessToken( prefs );
    	if( a!=null ){
    		Twitter twitter=new TwitterFactory().getInstance();
	    	//Twitter twitter = getTwitterInstance( prefs );
	    	twitter.setOAuthConsumer(TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(a);
	        try {
	        	//Looper.loop();
				twitter.updateStatus(tuit);
				Log.d("MGL", ""+ twitter.getScreenName().toString()		);
				return true;
	
			} catch (TwitterException e) {
				Log.e("MGL","TwitterExc: " + e.getMessage() 	 );
				return false;
			}   
    	}
		return false;

	}//fin mandaTuit
	
	public static boolean isAuthenticated( SharedPreferences prefs ){

		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
    		
    	if( secret.equals("") || token.equals("") ) return false;
    	
		return true;
	}

	private static AccessToken getAccessToken( SharedPreferences prefs ){
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
    		
    	if( secret.equals("") || token.equals("") ) return null;
    	Log.i("MGL", "TOKEN: " + token);
    	Log.i("MGL", "SECRET: " + secret);
		
    	return new AccessToken( token, secret );
	}
	
	public static Twitter getTwitterInstance( SharedPreferences prefs ){
		
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(TwitterData.CONSUMER_KEY);
        cb.setOAuthConsumerSecret(TwitterData.CONSUMER_SECRET);
        cb.setOAuthAccessToken(token);
        cb.setOAuthAccessTokenSecret(secret);

    	Twitter twitter = new TwitterFactory( cb.build() ).getInstance();
    	
    	return twitter;

	}
	
	public static boolean getTweets(String user,SharedPreferences prefs,int pagina) {

		AccessToken a = getAccessToken( prefs );
    	if( a!=null ){
    		Twitter twitter=new TwitterFactory().getInstance();
	    	//Twitter twitter = getTwitterInstance( prefs );
	    	twitter.setOAuthConsumer(TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(a);
	    	
		    try {
		    	//int pagina=1;		  
		    	Paging paging = new Paging(pagina, 10);
		        List<twitter4j.Status> statuses;
		        statuses = twitter.getUserTimeline(user,paging);
		        
		        /*
		        User user1 = twitter.showUser(twitter.getId());

		        
		        @SuppressWarnings("deprecation")
				String url = user1.getProfileImageURL();
		        
		        URL newurl = new URL(url); 
		        Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
		        ImageView bmImage = (ImageView) findViewById(R.id.imageView_imagen);
		        bmImage.setImageBitmap(mIcon_val);*/
		        //R.id.imageView_imagen.setImageBitmap(mIcon_val); 
		        
		        
		        
		        System.out.println("Showing @" + user + "'s user timeline.");
		        if(pagina==1){
		        	TweetsView.tweets.clear();
		        }//fin if
		        for (twitter4j.Status status : statuses) {

		            System.out.println("@" + status.getUser().getScreenName()
		                    + " - " + status.getText());
		            TweetsView.tweets.add(status.getText());
		        }//fin for
		        return true;
		        
		    } catch (TwitterException te) {
		        te.printStackTrace();
		        pagina--;
		        System.out.println("Failed to get timeline: " + te.getMessage());
		        return false;
		    }
	    	
    	}//fin if

    	return false;
	}//fin getTweets

	public static boolean followONUSIDAGuate(SharedPreferences prefs ){
		
    	AccessToken a = getAccessToken( prefs );
    	if( a!=null ){
    		Twitter twitter=new TwitterFactory().getInstance();
	    	//Twitter twitter = getTwitterInstance( prefs );
	    	twitter.setOAuthConsumer(TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(a);
	        try {
	        	//Looper.loop();
	        	twitter.createFriendship("ONUSIDAGuate");
				Log.d("MGL", twitter.getScreenName().toString()+" Following: @ONUSIDAGuate");
				return true;
	
			} catch (TwitterException e) {
				Log.e("MGL","TwitterExc: " + e.getMessage() 	 );
				return false;
			}   
    	}
		return false;

	}//fin followONUSIDAGuate
	
	public static boolean unfollowONUSIDAGuate(SharedPreferences prefs ){
		
    	AccessToken a = getAccessToken( prefs );
    	if( a!=null ){
    		Twitter twitter=new TwitterFactory().getInstance();
	    	//Twitter twitter = getTwitterInstance( prefs );
	    	twitter.setOAuthConsumer(TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(a);
	        try {
	        	//Looper.loop();
	        	twitter.destroyFriendship("ONUSIDAGuate");
				Log.d("MGL", twitter.getScreenName().toString()+" Following: @ONUSIDAGuate");
				return true;
	
			} catch (TwitterException e) {
				Log.e("MGL","TwitterExc: " + e.getMessage() 	 );
				return false;
			}   
    	}
		return false;

	}//fin followONUSIDAGuate	
	
	public static boolean isfollowingONUSIDAGuate( SharedPreferences prefs ){
		
    	AccessToken a = getAccessToken( prefs );
    	if( a!=null ){
    		Twitter twitter=new TwitterFactory().getInstance();
	    	//Twitter twitter = getTwitterInstance( prefs );
	    	twitter.setOAuthConsumer(TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(a);
	        try {
	        	//Looper.loop();
	        	
	        	if (twitter.showFriendship(twitter.getScreenName(), "ONUSIDAGuate").isSourceFollowingTarget()){
	        		Log.d("MGL", twitter.getScreenName().toString()+" Following: @ONUSIDAGuate");
	        		TweetsView.errorConexion=false;
	        		return true;
	        	}//fin if
	        	else{
	        		Log.d("MGL", twitter.getScreenName().toString()+"NO Following: @ONUSIDAGuate");
	        		TweetsView.errorConexion=false;
	        		return false;
	        	}//fin else
				//Log.d("MGL", twitter.getScreenName().toString()+" Following: @ONUSIDAGuate");
				//return true;
	
			} catch (TwitterException e) {
				Log.e("MGL","TwitterExc: " + e.getMessage() 	 );
				TweetsView.errorConexion=true;
				return false;
			}   
    	}
		return false;

	}//fin followONUSIDAGuate	
	
}
