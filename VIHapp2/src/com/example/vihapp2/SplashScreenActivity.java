package com.example.vihapp2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.content.SharedPreferences;

public class SplashScreenActivity extends Activity {

	private long splashDelay = 6000; //6 segundos
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		TimerTask task = new TimerTask() {
		@Override
			public void run() {
	    		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
	    		int inicio = sharedPreferences.getInt("inicio", 0);
			
	    		if (inicio!=1){
			        Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, InfoActivity.class);
			        startActivity(mainIntent);
			        finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
	    		}//fin if si es la primera vez q entra
	    		else{
			        Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, Principal.class);
			        startActivity(mainIntent);
			        finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
	    		}//fin else si no es la primera vez q entra
			}//fin run
		};

		    Timer timer = new Timer();
		    timer.schedule(task, splashDelay);//Pasado los 6 segundos dispara la tarea
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
