package com.example.vihapp2;

import com.example.vihapp2.Principal.MyExpandableListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ancla extends Activity {
	protected void onCreate(Bundle savedInstanceState) {			
		super.onCreate(savedInstanceState);			
		// Set up our adapter
		//setContentView(R.layout.activity_main);
		Intent intent;
		intent = new Intent(ancla.this, TweetsView.class);
		ancla.this.startActivity(intent);
		finish();

    }	
}
