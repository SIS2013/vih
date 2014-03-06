package com.example.vihapp2;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Boolean> {

	private Context context;
	private OAuthProvider provider;
	private OAuthConsumer consumer;
	private SharedPreferences prefs;

	public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,
			OAuthProvider provider, SharedPreferences prefs) {
		this.context = context;
		this.consumer = consumer;
		this.provider = provider;
		this.prefs = prefs;
	}

	/**
	 * Retrieve the oauth_verifier, and store the oauth and
	 * oauth_token_secret for future API calls.
	 */
	@Override
	protected Boolean doInBackground(Uri... params) {
		final Uri uri = params[0];
		final String oauth_verifier = uri
				.getQueryParameter(OAuth.OAUTH_VERIFIER);

		try {
			Log.i("MGL","Obtained oAuth Verifier: " + oauth_verifier);
			
			provider.retrieveAccessToken(consumer, oauth_verifier);

			final Editor edit = prefs.edit();
			edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			edit.commit();
			//TwitterActivity.logOutPrefs();
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			if( secret == null || token == null || secret.equals("") || token.equals("") ) return false;
			consumer.setTokenWithSecret(token, secret);
			context.startActivity(new Intent(context, TweetsView.class));			
			Log.i("MGL", "OAuth - Access Token Retrieved");
			


		} catch (Exception e) {
			Log.e("MGL", "OAuth - Access Token Retrieval Error", e);

		}

		return true;
	}

	protected void onPostExecute(Boolean result) {

        if(result){
			String tuit = "Enviado desde Android ";
			new MandaTuitTask(tuit, prefs).execute();
	         Toast.makeText(this.context,
    		"Conectado a twitter",
            Toast.LENGTH_SHORT).show();
	
	         TweetsView.logOutPrefs();
 

        }else{
        	Toast.makeText(this.context,
    		"Algo salió mal. Acceso a twitter NO conseguido.\n Intenta más tarde.",
            Toast.LENGTH_SHORT).show();
        }
   }		

}
