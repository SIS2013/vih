package com.example.vihapp2;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;


public class Maps extends FragmentActivity {
	private static final int MNU_OPC1 = 1;
	private static int clinica_cercana=0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        
        GoogleMap mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapa.setMyLocationEnabled(true);
               
        mapa.addMarker(new MarkerOptions()
        .position(new LatLng(14.61345336592434,-90.54224985396729))
        .title("Clínica de Infectología")
        .snippet("Hospital Roosvelt"));
        
        mapa.addMarker(new MarkerOptions()
        .position(new LatLng(14.639521532051692,-90.52093762116398))
        .title("Clínica Familiar Luis Ángel García")
        .snippet("Hospital San Juan de Dios"));
        
        mapa.addMarker(new MarkerOptions()
        .position(new LatLng(14.638114326079183,-90.51925418465578))
        .title("Asociación de Salud Integral")
        .snippet("Clínica Familiar VIH. Anexo HGSJD"));
       
        mapa.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
        	public void onInfoWindowClick(Marker marker){

        		if(marker.getId().equals("m0")){
                	AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                	        Maps.this);
                	alertDialog2.setTitle("Clínica de Infectología. Hospital Roosvelt");
                	alertDialog2.setMessage("Calzada Roosevelt y 5ª. Calle, zona 11\nPBX: 2321-7400");
                	alertDialog2.setPositiveButton("Aceptar",
                	        new DialogInterface.OnClickListener() {
                	            public void onClick(DialogInterface dialog, int which) {
                	            }//fin onClick
                	        });
                	alertDialog2.show();         			
        		}//fin if si es roosvelt
        		else if(marker.getId().equals("m1")){        			
                	AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                	        Maps.this);
                	alertDialog2.setTitle("Clínica Familiar Luis Ángel García. Hospital General San Juan de Dios");
                	alertDialog2.setMessage("1ª. Avenida 10-50, zona 1\nTeléfono: 22530423 al 29");
                	alertDialog2.setPositiveButton("Aceptar",
                	        new DialogInterface.OnClickListener() {
                	            public void onClick(DialogInterface dialog, int which) {
                	            }//fin onClick
                	        });
                	alertDialog2.show();        			
        		}//fin if si es San Juan de Dios
        		else if(marker.getId().equals("m2")){
        			
                	AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                	        Maps.this);
                	alertDialog2.setTitle("Asociación de Salud Integral anexo HGSJD");
                	alertDialog2.setMessage("2ª. Avenida 11-53, zona 1\nPBX: 2311-8102");
                	alertDialog2.setPositiveButton("Aceptar",
                	        new DialogInterface.OnClickListener() {
                	            public void onClick(DialogInterface dialog, int which) {
                	            }//fin onClick
                	        });
                	alertDialog2.show();         		
        		}//fin if si es clínica
        		
        		//Toast.makeText(Maps.this, "Detalle"+marker.getId(),Toast.LENGTH_SHORT).show();

        	}//fin switch
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    	menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Ver clínica más cercana")
	    			.setIcon(android.R.drawable.ic_menu_preferences);
        return true;
    }//fin onCreateOptionsMenu 
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch (item.getItemId()) {
            case MNU_OPC1:
            	AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
            	        Maps.this);
            	 
            	// Setting Dialog Title
            	alertDialog2.setTitle("Clínica más cercana:");
            	 
            	// Setting Dialog Message
            	
            	alertDialog2.setMessage(getLessDistance());
            	
            	// Setting Icon to Dialog
            	//alertDialog2.setIcon(R.drawable.delete);
            	 
            	// Setting Positive "Yes" Btn
            	alertDialog2.setPositiveButton("Ir a clínica",
            	        new DialogInterface.OnClickListener() {
            	            public void onClick(DialogInterface dialog, int which) {
            	                // Write your code here to execute after dialog
            	            	/*
            	                Toast.makeText(getApplicationContext(),
            	                        "You clicked on YES", Toast.LENGTH_SHORT)
            	                        .show();*/
            	            	GoogleMap mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            	            	CameraUpdate camUpd2=null;
            	                switch (clinica_cercana) {
            	                case 1:
                	                camUpd2 = CameraUpdateFactory.newLatLngZoom(new LatLng(14.61345336592434, -90.54224985396729), 18F);
                	                mapa.animateCamera(camUpd2);
            	                	break;
	        	                case 2:	        	                	
                	                camUpd2 = CameraUpdateFactory.newLatLngZoom(new LatLng(14.639521532051692,-90.52093762116398), 18F);
                	                mapa.animateCamera(camUpd2);
	        	                	break;
		    	                case 3:
                	                camUpd2 = CameraUpdateFactory.newLatLngZoom(new LatLng(14.638114326079183,-90.51925418465578), 18F);
                	                mapa.animateCamera(camUpd2);                	                
		    	                	break;
            					default:            						
            						break;
            	                }//fin switch
            	                mapa.animateCamera(camUpd2);
            	            }//fin onClick
            	        });
            	// Setting Negative "NO" Btn
            	alertDialog2.setNegativeButton("Cancelar",
            	        new DialogInterface.OnClickListener() {
            	            public void onClick(DialogInterface dialog, int which) {
            	                // Write your code here to execute after dialog
            	            	/*
            	                Toast.makeText(getApplicationContext(),
            	                        "You clicked on NO", Toast.LENGTH_SHORT)
            	                        .show();*/
            	                dialog.cancel();
            	            }
            	        });
            	 
            	// Showing Alert Dialog
            	alertDialog2.show();
                return true;           
            default:
                return super.onOptionsItemSelected(item);
        }//fin switch
    }//fin onOptionsItemSelected
        
    
    private String getLessDistance() {
    	
    	GoogleMap mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        Location user_location = mapa.getMyLocation();
        double user_latitud= user_location.getLatitude();
        double user_longitud= user_location.getLongitude();
        /*
        Toast.makeText(getApplicationContext(),
                "ULat="+user_latitud+" ULong"+user_longitud, Toast.LENGTH_SHORT)
                .show();
    	*/
		int distancia_roosvelt = getDistance(user_latitud,user_longitud,14.61345336592434,-90.54224985396729);
		int distancia_SanJuan= getDistance(user_latitud,user_longitud,14.639521532051692,-90.52093762116398);
		int distancia_Clinica= getDistance(user_latitud,user_longitud,14.638114326079183,-90.51925418465578);
		
		Log.d("Dis Roosvelt", distancia_roosvelt+"");
		Log.d("Dis San Juan", distancia_SanJuan+"");
		Log.d("Dis Clinica", distancia_Clinica+"");
		
		String mensaje_clinica_cercana="";
		if (distancia_roosvelt<distancia_SanJuan){
			if (distancia_roosvelt<distancia_Clinica){
				mensaje_clinica_cercana="Clínica de Infectología.\n\nHospital Roosvelt.\n\n"+Distance(distancia_roosvelt);
				clinica_cercana=1;
			}//fin if si roosvelt más cercano	
			else{
				mensaje_clinica_cercana="Asociación de Salud Integral.\n\nAnexo HGSJD\n\n"+Distance(distancia_SanJuan);				
				clinica_cercana=3;
			}//fin else si Clínica es más cercana
		}//fin if
		else{
			if (distancia_SanJuan<distancia_Clinica){
				mensaje_clinica_cercana="Clínica Familiar Luis Ángel García\n\nHospital General San Juan de Dios.\n\n"+Distance(distancia_Clinica);
				clinica_cercana=2;
			}//fin if si SanJuan más cercano
			else{
				mensaje_clinica_cercana="Asociación de Salud Integral.\n\nAnexo HGSJD\n\n"+Distance(distancia_Clinica);
				clinica_cercana=3;
			}//fin else si Clínica es más cercana
		}//fin else

		return mensaje_clinica_cercana;
	}//fin getLessDistance

    public static String Distance(int distance){  
    	  if(distance >= 1000){
    		 int k = distance / 1000;
    		int m = distance - (k*1000);
    	   m = m / 100;
    	   return String.valueOf(k) + (m>0?("."+String.valueOf(m)):"") + " Km" +(k>1?"s":"");
    	  } else {
    	   return String.valueOf(distance) + " metro"+(distance==1?"":"s");
    	  }  
    	 }
    
	public static int getDistance(double user_latitud,double user_longitud, double d, double e){
		  double Radius = 6371000; //Radio de la tierra
    	  double lat1 = user_latitud ;
    	  double lat2 = d ;
    	  double lon1 = user_longitud ;
    	  double lon2 = e ;
    	  double dLat = Math.toRadians(lat2-lat1);
    	  double dLon = Math.toRadians(lon2-lon1);
    	  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon /2) * Math.sin(dLon/2);
    	  double c = 2 * Math.asin(Math.sqrt(a));
    	  return (int) (Radius * c);  

    	 }    
	
	public void onActivityCreated (Bundle savedInstanceState){
		GoogleMap mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        Location user_location = mapa.getMyLocation();
       // R.dimen.user_latitud= (int) user_location.getLatitude();
       // R.dimen.user_longitud= (int) user_location.getLongitude();
        CameraUpdate camUpd2 = CameraUpdateFactory.newLatLngZoom(new LatLng(
        		user_location.getLatitude(), user_location.getLongitude()), 7F);
        mapa.animateCamera(camUpd2);
	}

}
