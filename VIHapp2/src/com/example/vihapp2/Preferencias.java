package com.example.vihapp2;


import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Preferencias extends Activity{

	RadioGroup genero;
	RadioGroup embarazada;
	RadioGroup diagnostico;
	TextView texto;
	public static ExpandableListActivity parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferencias);
		
		genero = (RadioGroup) findViewById(R.id.genero);		
		genero.setOnCheckedChangeListener(generoOnCheckedChangeListener);
		
		embarazada = (RadioGroup) findViewById(R.id.embarazada);
		embarazada.setOnCheckedChangeListener(embarazadaOnCheckedChangeListener);
		
		diagnostico = (RadioGroup) findViewById(R.id.diagnostico);
		diagnostico.setOnCheckedChangeListener(diagnosticoOnCheckedChangeListener);
		
		//texto = (TextView)findViewById(R.id.textView3);
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int inicio = sharedPreferences.getInt("inicio", 0);
	
		if (inicio==1){
			LoadPreferences();
		}//fin if si es la primera vez q entra
		else{
			SaveFirstPreferences();
		}//fin else
	}
	
	OnCheckedChangeListener embarazadaOnCheckedChangeListener = new OnCheckedChangeListener(){
		@Override		
		public void onCheckedChanged(RadioGroup grupo, int checkedId) {
			RadioButton seleccionado = (RadioButton) embarazada.findViewById(checkedId);
			int checkedIndex = embarazada.indexOfChild(seleccionado);
			SavePreferences("embarazada", checkedIndex);

		}//fin onCheckedChanged
	};
	
	OnCheckedChangeListener diagnosticoOnCheckedChangeListener = new OnCheckedChangeListener(){
		@Override		
		public void onCheckedChanged(RadioGroup grupo, int checkedId) {
			RadioButton seleccionado = (RadioButton) diagnostico.findViewById(checkedId);
			int checkedIndex = diagnostico.indexOfChild(seleccionado);
			SavePreferences("diagnostico", checkedIndex);
			//Principal.reiniciarActivity(parent);
		}//fin onCheckedChanged
	};	
	
	OnCheckedChangeListener generoOnCheckedChangeListener = new OnCheckedChangeListener(){
		@Override		
		public void onCheckedChanged(RadioGroup grupo, int checkedId) {
			
			RadioButton seleccionado = (RadioButton) genero.findViewById(checkedId);
			int checkedIndex = genero.indexOfChild(seleccionado);
			SavePreferences("genero", checkedIndex);
			
			if(checkedIndex==1){
				RadioButton GrupoEmbarazada = (RadioButton)embarazada.getChildAt(0);
				GrupoEmbarazada = (RadioButton)embarazada.getChildAt(0);
				GrupoEmbarazada.setEnabled(true);
				GrupoEmbarazada = (RadioButton)embarazada.getChildAt(1);
				GrupoEmbarazada.setEnabled(true);
				GrupoEmbarazada.setChecked(true);
			}//fin if
			else{
				RadioButton GrupoEmbarazada = (RadioButton)embarazada.getChildAt(0);
				GrupoEmbarazada = (RadioButton)embarazada.getChildAt(0);
				GrupoEmbarazada.setEnabled(false);
				GrupoEmbarazada = (RadioButton)embarazada.getChildAt(1);
				GrupoEmbarazada.setEnabled(false);
				GrupoEmbarazada.setChecked(true);
			}
			
			// TODO Auto-generated method stub
		}
	};
	
	private void SaveFirstPreferences(){
		SavePreferences("embarazada", 1);
		SavePreferences("diagnostico", 1);
		SavePreferences("genero", 0);
	}//fin SavePreferences	
	
	private void SavePreferences(String key, int value){
		  SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		  SharedPreferences.Editor editor = sharedPreferences.edit();
		  editor.putInt(key, value);
		  editor.commit(); 
	}//fin SavePreferences
		 
	 private void LoadPreferences(){
		  SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		  int savedRadioIndex = sharedPreferences.getInt("genero", 0);
		  RadioButton savedCheckedRadioButton = (RadioButton)genero.getChildAt(savedRadioIndex);
		  savedCheckedRadioButton.setChecked(true);
		  
		  savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
		  savedCheckedRadioButton = (RadioButton)embarazada.getChildAt(savedRadioIndex);
		  savedCheckedRadioButton.setChecked(true);
		  
		  savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		  savedCheckedRadioButton = (RadioButton)diagnostico.getChildAt(savedRadioIndex);
		  savedCheckedRadioButton.setChecked(true);
		  
		 }//fin LoadPreferences


/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
 
		switch(keyCode){
			case KeyEvent.KEYCODE_CAMERA:
				Toast.makeText(this, "Obturador presionado",
                                                        Toast.LENGTH_SHORT).show();
				return true;
			case KeyEvent.KEYCODE_VOLUME_UP:
				Toast.makeText(this, "Boton de Volumen Up presionado",
                                                        Toast.LENGTH_SHORT).show();
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				Toast.makeText(this, "Boton de Volumen Down presionado",
                                                        Toast.LENGTH_SHORT).show();
				return true;
			case KeyEvent.KEYCODE_BACK:
				Toast.makeText(this, "Boton de Back presionado",
                                                        Toast.LENGTH_SHORT).show();
				return true;				
		}
		return super.onKeyDown(keyCode, event);
	}	*/
	
	@Override 
	public void onBackPressed ()  { 
		// ignoramos el botón retroceder
		Toast.makeText(this, "Preferencias Guardadas",
                Toast.LENGTH_SHORT).show();
		
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int inicio = sharedPreferences.getInt("inicio", 0);
	
		if (inicio==1){
			Principal.reiniciarActivity(parent);
		}//fin if si es la primera vez q entra
		else{			
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putInt("inicio", 1);
			editor.commit();
	        Intent mainIntent = new Intent().setClass(Preferencias.this, Principal.class);
	        startActivity(mainIntent);
		}//fin else
		this.finish();

	}
	
}
