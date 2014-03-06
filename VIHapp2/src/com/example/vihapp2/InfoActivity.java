package com.example.vihapp2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class InfoActivity extends Activity {
	private Button entrar;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		this.entrar = (Button)findViewById(R.id.entrar);
		this.entrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

            	AlertDialog.Builder alertDialog = new AlertDialog.Builder(
            	        InfoActivity.this);
            	alertDialog.setTitle("Configuración Inicial");
            	alertDialog.setMessage("Por favor establezca sus preferencias antes de utilizar la aplicación.");
            	alertDialog.setPositiveButton("Aceptar",
            	        new DialogInterface.OnClickListener() {
            	            public void onClick(DialogInterface dialog, int which) {
            			        Intent mainIntent = new Intent().setClass(InfoActivity.this, Preferencias.class);
            			        startActivity(mainIntent);
            			        finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
            	            }//fin onClick
            	        });
            	alertDialog.show();
				


			}//fin onClick
		});		
	}//fin onCreate

}