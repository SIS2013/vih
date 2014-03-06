package com.example.vihapp2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.example.vihapp2.TwitterData;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterActivity extends Activity {

	private static CommonsHttpOAuthConsumer httpOauthConsumer;
	private static OAuthProvider httpOauthprovider;
	private static Button btnOAuth;
	private static Button getTweets;
	private static Button postTweets;
	private TextView codigoRespuesta;
	public static SharedPreferences preferencias;
	public static int pagina=1;
	public static TwitterActivity view;
	private long splashDelay = 3000; //3 segundos
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter);
		preferencias = getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
		this.btnOAuth = (Button)findViewById(R.id.btnOAuth);
		this.getTweets = (Button)findViewById(R.id.getTweets);
		this.postTweets = (Button)findViewById(R.id.postTweets);
		this.codigoRespuesta = (TextView)findViewById(R.id.codigoRespuesta);
		
		if (TwitterUtils.isAuthenticated(preferencias)){
			logOutPrefs();
		}//fin if
		else{
			logInPrefs();
		}//fin else
		

		
		this.btnOAuth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				preferencias = getSharedPreferences("TwitterPrefs", MODE_PRIVATE);

				if (TwitterUtils.isAuthenticated(preferencias)){
					logOutApp();
				}//fin if
				else{
					autorizarApp();
					if (TwitterUtils.isAuthenticated(preferencias)){
						logOutPrefs();
					}//fin if
					else{
						logInPrefs();
					}//fin else
				}//fin else
		    	Log.i("MGL", "depues de mandar");
			}
		});
		this.getTweets.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				preferencias = getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
				if	(TwitterUtils.isAuthenticated(preferencias)){
					boolean isTweets=TwitterUtils.getTweets("ONUSIDAGuate",preferencias,pagina);
					if (isTweets){
						Intent intent;
						view=TwitterActivity.this;
						intent = new Intent(TwitterActivity.this, TweetsView.class);
						TwitterActivity.this.startActivity(intent);
					}//fin if
					else{
				        Toast.makeText(getApplicationContext(),
		                        "No se pueden recuperar los tweets en este momento. \n" +
		                        "Por favor intente más tarde", Toast.LENGTH_SHORT)
		                        .show();
					}//fin else
			    	Log.i("MGL", "presiona GetTweets");
				}//fin if
				else{
					Toast.makeText(getApplicationContext(),
	                        "No logueado", Toast.LENGTH_SHORT)
	                        .show();
				}//fin else
			}//fin onClick
			
		});
		
		
		this.postTweets.setOnClickListener(new View.OnClickListener() {
			int cantCaracter=0;
			int cantCaracter2=140;
			AlertDialog dialogoTweet;
			
			ArrayList <String> prePost = new ArrayList <String>();
				@Override
				public void onClick(View v) {

				
				/****************** SELECCION DE HASHTAGS ********************/
				
				final CharSequence[] hashtags = {"#Onusida","#Sida","#VIH","#VozJovenVIH"};
				//final CharSequence[] hashtags = {"#hashtag1","#hashtag2","#hashtag3","#hashtag4"};
				final boolean [] selected2 = {false,false,false,false};
				 
				AlertDialog.Builder hashtagsD = new AlertDialog.Builder(TwitterActivity.this);
				hashtagsD.setTitle("Selecciona hashtags");
				hashtagsD.setMultiChoiceItems(hashtags, selected2, new DialogInterface.OnMultiChoiceClickListener() {
			    public void onClick(DialogInterface dialogInterface, int item, boolean b) {
			        Log.d("Myactivity", String.format("%s: %s", hashtags[item], b));
			    }//fin onClick
			    });				
			    hashtagsD.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                	            public void onClick(DialogInterface dialog, int which) {
                					/****************** SELECCION DE MENCIONES ********************/
                	            	//final CharSequence[] menciones = {"@AldanaWerner"};
                					final CharSequence[] menciones = {"@ONUSIDAGuate"};
                					final boolean [] selected = {true};
                					 
                					AlertDialog.Builder builder = new AlertDialog.Builder(TwitterActivity.this);
                					builder.setTitle("Selecciona menciones");
                				    builder.setMultiChoiceItems(menciones, selected, new DialogInterface.OnMultiChoiceClickListener() {
                				    public void onClick(DialogInterface dialogInterface, int item, boolean b) {
                				        Log.d("Myactivity", String.format("%s: %s", menciones[item], b));
                				    }//fin onClick
                				    });				
                					builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                	                	            public void onClick(DialogInterface dialog, int which) {
                	                	            	
                	                	            	for(int i=0;i<menciones.length;i++){
            	                	            			if(selected[i]==true){
            	                	            				cantCaracter+=menciones[i].length()+1;
            	                	            				prePost.add(menciones[i].toString());
            	                	            			}//fin if
                	                	            		Log.d("Mentions: "+menciones[i], " -- "+selected[i]);
                	                	            	}//fin for                 	                	            	
                	                	            	
                	                	            	for(int i=0;i<hashtags.length;i++){
                	                	            			if(selected2[i]==true){
                	                	            				cantCaracter+=hashtags[i].length()+1;
                	                	            				prePost.add(hashtags[i].toString());
                	                	            			}//fin if                	                	            	                	                	            	
                	                	            		Log.d("Hashtag: "+hashtags[i], " -- "+selected2[i]);
                	                	            	}//fin for
                  	                	            	cantCaracter=140-cantCaracter;
                	                	            	Log.d("CantCaracters: ", cantCaracter+"");
                	                	            	                	                	            	                	                	            	
                	                	            	final TextView labelCaracter =new TextView(TwitterActivity.this);
                	                	            	final EditText input = new EditText(TwitterActivity.this);
                	                	            	/***** Setting hashtags and mentions to input **********/
                	                	            	for(int i=0;i<prePost.size();i++){
                	                	            		input.setText(input.getText()+prePost.get(i)+" ");
                	                	            	}//fin for
                	                	            	/***************/
                	                	                input.addTextChangedListener(new TextWatcher() {

                	                	                    @Override
                	                	                    public void afterTextChanged(Editable s) {
                	                	                        // TODO Auto-generated method stub

                	                	                    }//fin afterTextChanged

                	                	                    @Override
                	                	                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                	                	                        // TODO Auto-generated method stub

                	                	                    }//fin beforeTextChanged

                	                	                    @Override
                	                	                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                	                	                    	cantCaracter2=140;
                	                	                    	cantCaracter2=cantCaracter2-s.length();
                	                	                    	
                	                	                    	Log.d("CantCaracters2: ", cantCaracter2+"");
                	                	                    	dialogoTweet.setMessage(cantCaracter2+"");
                	                	                    	if(input.getText().length()<141){
                	                	                    		dialogoTweet.getButton(-1).setEnabled(true);
                	                	                    	}//fin if
                	                	                    	else{
                	                	                    		dialogoTweet.getButton(-1).setEnabled(false);
                	                	                    	}//fin else
                	                	                    }//fin onTextChanged 

                	                	                });
                	                	            	
                	                	            	
                	                	            	input.setLines(5);
                	                	            	input.setSelection(input.getText().length());
                	                	            	labelCaracter.setText(cantCaracter+"");
                	                	            	dialogoTweet=new AlertDialog.Builder(TwitterActivity.this)
                	                	                .setTitle("Escribe tu tweet...")
                	                	                .setView(input)
                	                	                .setMessage(cantCaracter+"")
                	                	                //.setView(labelCaracter)
                	                	                .setPositiveButton("Tweet", new DialogInterface.OnClickListener() {
                	                	                    public void onClick(DialogInterface dialog, int whichButton) {
                	                	                    	Log.d("Enviando tweet: ", input.getText().toString());                	                	                    	
                	                	                    	
                	                	                		final ProgressDialog dialog3 = ProgressDialog.show(TwitterActivity.this, "","Enviando tweet..." , true);
                	                	                		dialog3.show();
                	                	                		Handler handler = new Handler();
                	                	                		handler.postDelayed(new Runnable() {
                	                	                		    public void run() {
                	                	                		        //your code here    
	                        	                	                    	boolean respuestaPostTweet=TwitterUtils.mandaTuit(input.getText().toString(), preferencias);
	                        	                	                    	if (respuestaPostTweet){
	                        	                	    				        Toast.makeText(getApplicationContext(),"Tweet enviado", Toast.LENGTH_SHORT).show();
	                        	                	                    	}//fin if
	                        	                	                    	else{
	                        	                	                    		Toast.makeText(getApplicationContext(),"Tweet No enviado.\n Por favor intente más tarde.", Toast.LENGTH_SHORT).show();
	                        	                	                    	}//fin else
	                        	                	                        prePost.clear();
	                        	                	                        cantCaracter=0;
                	                	                		                dialog3.dismiss();
                	                	                		    }   
                	                	                		}, 3000);  // 3000 milliseconds
                	                	                    	
                	                	                    	                	                	                    	
                	                	                    }//fin Postear tweet
                	                	                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                	                	                    public void onClick(DialogInterface dialog, int whichButton) {
                	                	                    	
                	                	                        prePost.clear();
                	                	                        cantCaracter=0;
                	                	                    }//fin cancelar post tweet
                	                	                }).show();
                	                	            	
                	                	            	
                	                	            	
                	                	            }//fin onClick "Aceptar Menciones"
                	                	        });			 
                					builder.show();	                	            	
                	            	
                	            }//fin onClick "Aceptar Hashtags"
                	        });			 
			    hashtagsD.show();
						    
			}//fin onClick
		});		
	}//fin onCreate
	

	protected void logOutApp() {
		preferencias = getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
		final Editor edit = preferencias.edit();
		edit.putString(OAuth.OAUTH_TOKEN,"");
		edit.putString(OAuth.OAUTH_TOKEN_SECRET, "");
		//edit.remove("TwitterPrefs");
		edit.commit();
		
		
		final ProgressDialog dialog = ProgressDialog.show(this, "","Loging out..." , true);
		dialog.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		    public void run() {
		        //your code here    
		    	logInPrefs();
		    	Toast.makeText(getApplicationContext(),
				"Desconectado de Twitter",
		        Toast.LENGTH_SHORT).show();
		                dialog.dismiss();
		    }   
		}, 3000);  // 3000 milliseconds

	}//fin lofOutApp


	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the
	 * request token). The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		final Uri uri = intent.getData();
		preferencias = this.getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
	
		if (uri != null && uri.toString().indexOf(TwitterData.CALLBACK_URL) != -1) {
			Log.i("MGL", "Callback received : " + uri);
		
			new RetrieveAccessTokenTask(this, getConsumer(), getProvider(),
					preferencias).execute(uri);
		}
	}

	protected void autorizarApp() {
		try {

			
			// retrieve the request token
			final Context context=this;
			final ProgressDialog dialog = ProgressDialog.show(this, "","Loging in..." , true);
			dialog.show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    public void run() {
			        //your code here    	
			    	getProvider().setOAuth10a(true);
			    	
			    	new OAuthRequestTokenTask(context, getConsumer(), getProvider()).execute();
			                dialog.dismiss();
			    }   
			}, 3000);  // 3000 milliseconds
			
			

		} catch (Exception e) {
			codigoRespuesta.setText(e.getMessage());
		}		
	}
	
	
	/**
	 * @return the provider (initialize on the first call)
	 */
	public static OAuthProvider getProvider() {
		if (httpOauthprovider == null) {
			httpOauthprovider = new DefaultOAuthProvider(
					TwitterData.REQUEST_URL, TwitterData.ACCESS_URL,
					TwitterData.AUTHORIZE_URL);
			httpOauthprovider.setOAuth10a(true);
		}
		return httpOauthprovider;
	}

	/**
	 * @param context
	 *            the context
	 * @return the consumer (initialize on the first call)
	 */
	public static CommonsHttpOAuthConsumer getConsumer() {
		if (httpOauthConsumer == null) {
			httpOauthConsumer = new CommonsHttpOAuthConsumer(
					TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
		}
		return httpOauthConsumer;
	}
//    @Override
//    public void onBackPressed() {
//		Intent intent;			
//    	intent =new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
//
//    }
	
	public static void logOutPrefs(){
		getTweets.setEnabled(true);	
		postTweets.setEnabled(true);	
		btnOAuth.setText("Log out");
	}//fin logOutPrefs
	
	public static void logInPrefs(){
		getTweets.setEnabled(false);
		postTweets.setEnabled(false);
		btnOAuth.setText("Log in");
	}//fin logInPrefs
	

	
}

