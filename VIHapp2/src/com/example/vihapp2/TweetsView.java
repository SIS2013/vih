package com.example.vihapp2;



	import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


	public class TweetsView  extends Activity {
		public static ListView lista;
		private Button getMoreTweets;		
		public static boolean errorConexion=false;
		public static ArrayList<String> tweets = new ArrayList<String>();
		public static SharedPreferences preferencias;
		private TextView codigoRespuesta;
		private static CommonsHttpOAuthConsumer httpOauthConsumer;
		private static OAuthProvider httpOauthprovider;
		private static Button btnOAuth;
		private static Button getTweets;
		private static Button postTweets;
		private static Button follow;
		public static int pagina=1;
		public static TweetsView view;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.listado);
	        preferencias = getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
			this.btnOAuth = (Button)findViewById(R.id.log);
			this.getTweets = (Button)findViewById(R.id.getMoreTweets);
			this.postTweets = (Button)findViewById(R.id.postTweet);
			this.follow = (Button)findViewById(R.id.follow);
	        
	        ArrayList<Lista_entrada> datos = getListTweets();

	        lista = (ListView) findViewById(R.id.ListView_listado);
	        
	        lista.setAdapter(new Lista_adaptador(this, R.layout.tweetlist, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima()); 

			            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
			            if (texto_inferior_entrada != null)
			            	texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); 

			            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
			            if (imagen_entrada != null)
			            	imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
			        }
			        this.notifyDataSetChanged();
				}
				});

	        lista.setOnItemClickListener(new OnItemClickListener() { 
				@Override
				public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
					Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion); 

	                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
	                Toast toast = Toast.makeText(TweetsView.this, texto, Toast.LENGTH_LONG);
	                toast.show();
				}
	        });	       
	        this.getTweets = (Button)findViewById(R.id.getMoreTweets);		
			this.getTweets.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					pagina++;
					boolean isTweets=TwitterUtils.getTweets("ONUSIDAGuate",preferencias,pagina);
			    	Log.i("MGL", "depues de mandar");
					if (isTweets){
						lista.invalidateViews();
						Intent intent;
						intent = new Intent(TweetsView.this, ancla.class);
						TweetsView.this.startActivity(intent);
						finish();
					}//fin if
					else{
				        Toast.makeText(getApplicationContext(),
		                        "No se pueden recuperar los tweets en este momento. \n" +
		                        "Por favor intente más tarde", Toast.LENGTH_SHORT)
		                        .show();
					}//fin else
			    	Log.i("MGL", "presiona GetTweets");			    	
			    	/*lista.invalidateViews();
					Intent intent;
					intent = new Intent(TweetsView.this, TweetsView.class);
					TweetsView.this.startActivity(intent);
			    	finish();*/
				}
			});	
			lista.setSelectionFromTop(((pagina-1)*10), 0);
			
			
	        if (TwitterUtils.isAuthenticated(preferencias)){
	        	setListActive();
	        	setFollowing(); 
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
					 
					AlertDialog.Builder hashtagsD = new AlertDialog.Builder(TweetsView.this);
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
	                					 
	                					AlertDialog.Builder builder = new AlertDialog.Builder(TweetsView.this);
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
	                	                	            	                	                	            	                	                	            	
	                	                	            	final TextView labelCaracter =new TextView(TweetsView.this);
	                	                	            	final EditText input = new EditText(TweetsView.this);
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
	                	                	            	dialogoTweet=new AlertDialog.Builder(TweetsView.this)
	                	                	                .setTitle("Escribe tu tweet...")
	                	                	                .setView(input)
	                	                	                .setMessage(cantCaracter+"")
	                	                	                //.setView(labelCaracter)
	                	                	                .setPositiveButton("Tweet", new DialogInterface.OnClickListener() {
	                	                	                    public void onClick(DialogInterface dialog, int whichButton) {
	                	                	                    	Log.d("Enviando tweet: ", input.getText().toString());                	                	                    	
	                	                	                    	
	                	                	                		final ProgressDialog dialog3 = ProgressDialog.show(TweetsView.this, "","Enviando tweet..." , true);
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
			/*this.getTweets.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					preferencias = getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
					if	(TwitterUtils.isAuthenticated(preferencias)){
						boolean isTweets=TwitterUtils.getTweets("ONUSIDAGuate",preferencias,pagina);
						if (isTweets){
							Intent intent;
							view=TweetsView.this;
							intent = new Intent(TweetsView.this, TweetsView.class);
							TweetsView.this.startActivity(intent);
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
				
			});	*/		
	    }//fin onCreate
	    public ArrayList getListTweets(){
	        ArrayList<Lista_entrada> ListaTweets = new ArrayList<Lista_entrada>();  

	        
	        for(int i=0;i<tweets.size();i++) {
	        	ListaTweets.add(new Lista_entrada(R.drawable.icon,"", tweets.get(i)));
	        }
	        /*
	        ListaTweets.add(new Lista_entrada(R.drawable.icon, "BUHO", "Búho es el nombre común de aves de la familia Strigidae, del orden de las estrigiformes o aves rapaces nocturnas. Habitualmente designa especies que, a diferencia de las lechuzas, tienen plumas alzadas que parecen orejas."));
	        ListaTweets.add(new Lista_entrada(R.drawable.icon, "COLIBRÍ", "Los troquilinos (Trochilinae) son una subfamilia de aves apodiformes de la familia Trochilidae, conocidas vulgarmente como colibríes, quindes, tucusitos, picaflores, chupamirtos, chuparrosas, huichichiquis (idioma nahuatl), mainumby (idioma guaraní) o guanumby. Conjuntamente con las ermitas, que pertenecen a la subfamilia Phaethornithinae, conforman la familia Trochilidae que, en la sistemática de Charles Sibley, se clasifica en un orden propio: Trochiliformes, independiente de los vencejos del orden Apodiformes. La subfamilia Trochilinae incluye más de 100 géneros que comprenden un total de 330 a 340 especies."));
	        ListaTweets.add(new Lista_entrada(R.drawable.icon, "CUERVO", "El cuervo común (Corvus corax) es una especie de ave paseriforme de la familia de los córvidos (Corvidae). Presente en todo el hemisferio septentrional, es la especie de córvido con la mayor superficie de distribución. Con el cuervo de pico grueso, es el mayor de los córvidos y probablemente la paseriforme más pesada; en su madurez, el cuervo común mide entre 52 y 69 centímetros de longitud y su peso varía de 0,69 a 1,7 kilogramos. Los cuervos comunes viven generalmente de 10 a 15 años pero algunos individuos han vivido 40 años. Los juveniles pueden desplazarse en grupos pero las parejas ya formadas permanecen juntas toda su vida, cada pareja defendiendo un territorio. Existen 8 subespecies conocidas que se diferencian muy poco aparentemente, aunque estudios recientes hayan demostrado diferencias genéticas significativas entre las poblaciones de distintas regiones."));
	        */
	    	
			return ListaTweets;
	    	
	    }//fin getListTweets
	    /*@Override
	    public void onBackPressed() {
	    	/*Intent intent;
			intent = new Intent(TweetsView.this, Principal.class);
			TweetsView.this.startActivity(intent);
	    	finish();
	 
	    }*/
	    
		public void followButton(){
	    	//this.follow.setBackgroundColor(R.drawable.button);
			if(!errorConexion){
				this.follow.setText("Follow");
				this.follow.setBackgroundResource(R.drawable.button_normal);
			}//fin if
	    	else{
	    		this.follow.setText("");	    		
	    		this.follow.setBackgroundColor(Color.GRAY);
	    	}			
	    }//fin followButton
	    
	    public void unfollowButton(){
	    	//this.follow.setBackgroundColor(R.drawable.button);
	    	if(!errorConexion){
	    		this.follow.setText("Unfollow");	    		
	    		this.follow.setBackgroundResource(R.drawable.button_unfollow);
	    	}//fin if
	    	else{
	    		this.follow.setText("");	    		
	    		this.follow.setBackgroundColor(Color.GRAY);
	    	}
	    }//fin followButton	    

	    public void setFollowing(){
        	
	        
	        if(TwitterUtils.isfollowingONUSIDAGuate(preferencias)){
	        	unfollowButton();
	        }
	        else{
	        	followButton();
	        }
	        
			this.follow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(TwitterUtils.isfollowingONUSIDAGuate(preferencias)){
						if(TwitterUtils.unfollowONUSIDAGuate(preferencias)){
							followButton();
							Toast toast = Toast.makeText(TweetsView.this, "Unfollowing @ONUSIDAGuate", Toast.LENGTH_LONG);
							toast.show();
						}//fin if
						else{							
							Toast toast = Toast.makeText(TweetsView.this, "No se pudo completar la operación.\nPor favor intenta más tarde.", Toast.LENGTH_LONG);
							toast.show();
						}
					}//fin if
					else{
						if(TwitterUtils.followONUSIDAGuate(preferencias)){
							unfollowButton();
							Toast toast = Toast.makeText(TweetsView.this, "Following @ONUSIDAGuate", Toast.LENGTH_LONG);
							toast.show();
						}//fin if
						else{
							Toast toast = Toast.makeText(TweetsView.this, "No se pudo completar la operación.\nPor favor intenta más tarde.", Toast.LENGTH_LONG);
							toast.show();
						}//fin else
					}//fin else
				}//
			});		    	
	    }//fin setFollowing
	    
	    public void setListActive(){
/*
	        ArrayList<Lista_entrada> datos = getListTweets();

	        lista = (ListView) findViewById(R.id.ListView_listado);
	        
	        lista.setAdapter(new Lista_adaptador(this, R.layout.tweetlist, datos){
				@Override
				public void onEntrada(Object entrada, View view) {
			        if (entrada != null) {
			            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
			            if (texto_superior_entrada != null) 
			            	texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima()); 

			            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
			            if (texto_inferior_entrada != null)
			            	texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo()); 

			            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
			            if (imagen_entrada != null)
			            	imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
			        }
			        this.notifyDataSetChanged();
				}
				});

	        lista.setOnItemClickListener(new OnItemClickListener() { 
				@Override
				public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
					Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion); 

	                CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
	                Toast toast = Toast.makeText(TweetsView.this, texto, Toast.LENGTH_LONG);
	                toast.show();
				}
	        });	       
	        this.getTweets = (Button)findViewById(R.id.getMoreTweets);		
			this.getTweets.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					pagina++;
					TwitterUtils.getTweets("ONUSIDAGuate",preferencias,pagina);
			    	Log.i("MGL", "depues de mandar");		    	
			    	lista.invalidateViews();
					Intent intent;
					intent = new Intent(TweetsView.this, TweetsView.class);
					TweetsView.this.startActivity(intent);
			    	//finish();
				}
			});	
			lista.setSelectionFromTop(((pagina-1)*10), 0);	  */  	
	    }//fin setListActive

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
		}//fin autorizarapp		
		
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
		
		public static void logOutPrefs(){
			getTweets.setEnabled(true);	
			postTweets.setEnabled(true);
			follow.setEnabled(true);
			btnOAuth.setText("Log out");
		}//fin logOutPrefs
		
		public static void logInPrefs(){
			getTweets.setEnabled(false);
			postTweets.setEnabled(false);
			follow.setEnabled(false);
			btnOAuth.setText("Log in");
		}//fin logInPrefs	    
	    
	}

