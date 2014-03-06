package com.example.vihapp2;
 

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;


public class Principal extends ExpandableListActivity implements
OnChildClickListener {
 
	private static final int MNU_OPC1 = 1;
	private static final int MNU_OPC2 = 2;
	private static final int MNU_OPC3 = 3;
	private static final int MNU_OPC4 = 4;
	private static final int SMNU_OPC1 = 5;
	private static final int SMNU_OPC2 = 6;
	private static final int SMNU_OPC3 = 7;
	private static final int SMNU_OPC4 = 8;
	private static final int SMNU_OPC5 = 9;
	ExpandableListAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {			
		super.onCreate(savedInstanceState);			
		// Set up our adapter
		//setContentView(R.layout.activity_main);
        mAdapter = new MyExpandableListAdapter();
        
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());

    }		   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    	menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Preferencias")
	    			.setIcon(android.R.drawable.ic_menu_preferences);
    	    menu.add(Menu.NONE, MNU_OPC2, Menu.NONE, "Foro en Twitter")
    	            .setIcon(R.drawable.icono_twitter);
    	    menu.add(Menu.NONE, MNU_OPC3, Menu.NONE, "Localizador de cl�nicas")
    	            .setIcon(android.R.drawable.ic_menu_mapmode);
    	    SubMenu smnu = menu.
    	    	    addSubMenu(Menu.NONE, MNU_OPC4, Menu.NONE, "Cambiar Idioma")
    	    	        .setIcon(android.R.drawable.ic_menu_set_as);
    	    	smnu.add(Menu.NONE, SMNU_OPC1, Menu.NONE, "Espa�ol");
    	    	smnu.add(Menu.NONE, SMNU_OPC2, Menu.NONE, "K'iche'");
    	    	smnu.add(Menu.NONE, SMNU_OPC3, Menu.NONE, "Q'eqchi'");
    	    	smnu.add(Menu.NONE, SMNU_OPC4, Menu.NONE, "Kaqchiquel");
    	    	smnu.add(Menu.NONE, SMNU_OPC5, Menu.NONE, "Mam");

        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//fin onCreateOptionsMenu 
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
        switch (item.getItemId()) {
            case MNU_OPC1:
            	Preferencias.parent=this;
    			intent = new Intent(Principal.this, Preferencias.class);
    			Principal.this.startActivity(intent);    			
                return true;
            case MNU_OPC2:
            	/*intent = new Intent(Principal.this, TweetsView.class);
    			Principal.this.startActivity(intent);*/
            	TweetsView.pagina=1;
            	
            	intent = new Intent(Principal.this, TweetsView.class);
    			Principal.this.startActivity(intent);
            	
    			/*intent = new Intent(Principal.this, FacebookActivity.class);
    			Principal.this.startActivity(intent); */
                return true;
            case MNU_OPC3: 
    			intent = new Intent(Principal.this, Maps.class);
    			Principal.this.startActivity(intent);               
                return true;
            case MNU_OPC4:                
                return true;
            case SMNU_OPC1:  
            	SavePreferences("idioma",0);
            	Toast.makeText(this, "Cambiado a Espa�ol",
                        Toast.LENGTH_SHORT).show();
            	reiniciarActivity(this);
                return true;     
            case SMNU_OPC2:   
            	SavePreferences("idioma",1);
            	Toast.makeText(this, "Ch'ab'al K'iche'",
                        Toast.LENGTH_SHORT).show();  
            	reiniciarActivity(this);
                return true; 
            case SMNU_OPC3:  
            	SavePreferences("idioma",2);
            	Toast.makeText(this, "Jalajik Q'eqchi'",
                        Toast.LENGTH_SHORT).show();  
            	reiniciarActivity(this);            	
                return true; 
            case SMNU_OPC4: 
            	SavePreferences("idioma",3);
            	Toast.makeText(this, "Jaloj Kaqchiquel'",
                        Toast.LENGTH_SHORT).show();  
            	reiniciarActivity(this);             	
                return true; 
            case SMNU_OPC5: 
            	SavePreferences("idioma",4);
            	Toast.makeText(this, "Ank�al Mam'",
                        Toast.LENGTH_SHORT).show();  
            	reiniciarActivity(this);             	
                return true;                 
            default:
                return super.onOptionsItemSelected(item);
        }//fin switch
    }//fin onOptionsItemSelected
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("");
        menu.add(0, 0, 0, "Agregar a Agenda");
    }//fin onCreateContextMenu
    
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
            int childPosition, long id) {
    	
    	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int idioma = sharedPreferences.getInt("idioma", 0);
		String childSelected=mAdapter.getChild(groupPosition, childPosition).toString();
		String detalle=Child.getDetalleChildren(childSelected, idioma);
		
    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(
    	        Principal.this);
    	alertDialog.setTitle(childSelected);
    	alertDialog.setMessage(detalle);
    	alertDialog.setPositiveButton("Aceptar",
    	        new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int which) {
    	            }//fin onClick
    	        });
    	alertDialog.show();
		
    	//Toast.makeText(this, "Presiona: Posicion grupo: "+groupPosition+"\nPosicion hijo: "+childPosition+"\n"+childSelected, Toast.LENGTH_SHORT).show();
    	
        return true;
    }
    

    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();
        
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            //int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
            Toast.makeText(this, title + " " + (groupPos+1),
                    Toast.LENGTH_SHORT).show();
            
            
        	Calendar cal = Calendar.getInstance();              
        	Intent AgendaEvento = new Intent(Intent.ACTION_EDIT);
        	AgendaEvento.setType("vnd.android.cursor.item/event");
        	AgendaEvento.putExtra("beginTime", cal.getTimeInMillis());
        	AgendaEvento.putExtra("allDay", true);
        	AgendaEvento.putExtra("rrule", "FREQ=YEARLY");
        	AgendaEvento.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        	AgendaEvento.putExtra("title", "VIH-"+title);
        	startActivity(AgendaEvento);
            
            return true;
        }//fin if 
        else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            /*int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();*/
            return true;
        }//fin else if
        
        return false;
    }//fin onContextItemSelected


    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        /*private String[] groups = { "Paso 1", "Paso 2" };
        private String[][] children = {
                { "Paso 1.1", "Paso 1.2", "Paso 1.3" },
                { "Paso 2.1", "Paso 2.2" }
        };*/
    	SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int idioma = sharedPreferences.getInt("idioma", 0);
		
        public String[] groups = getGroups(idioma);
        public String[][] children = getChildren(idioma);
        
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }//fin getChild

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }//fin getChildId

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }//fin getChildrenCount

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(Principal.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }//fin getGenericView
        
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }//fin getChildView

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }//fin getGroup

        public int getGroupCount() {
            return groups.length;
        }//fingetGroupCount

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }//getGroupId

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }//fin getGroupView

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }//fin isChildSelected

        public boolean hasStableIds() {
            return true;
        }//fin hasStableIds
        
    }//fin MyExpandableListAdapter

/******************** GRUPOS DE IDIOMAS *******************************************/
	public String[] getGroups(int idioma) {
		
		switch (idioma) {
        case 1:
        	return getGroupKiche();
        case 2:
        	return getGroupQeqchi();  
        case 3:
        	return getGroupKaqchiquel();      
        case 4:
        	return getGroupMam();         	
		default:
        	return getGroupEspanol();
        }//fin switch					 
		//return null;
	}//fin getGroups

	/****** IDIOMA KICHE*****/
	
	private String[] getGroupKiche() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		  
		if(savedRadioIndex==1){
				//Si el diagnostico no lo sabe
			String[] groups = { "Jun 1", "kaib' 2 (Si jun 1 Qastzij)", "Oxib' 3 (Si kaib 2 Qastzij)" };
			return groups;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada
				String[] groups = { "Uchapale'm","15 Q'ij","45 Q'ij","3 Ik�","6 Ik�","Uterne'xik 3 Ik�","Uterne'xik 6 Ik�"};
				return groups;
			}//fin if
			else{
				//Si est� embarazada
				String[] groups = { "Nab'e Trimestre","kaib' Trimestre","Oxib' Trimestre"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupKiche

	/****** IDIOMA Q'EQCHI'*****/
	
	private String[] getGroupQeqchi() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		  
		if(savedRadioIndex==1){
				//Si el diagnostico no lo sabe
			String[] groups = { "B�eeresinkil 1", "B�eeresinkil 2 (B�eeresinkil 1 positivo)", "B�eeresinkil 3 (B�eeresinkil 2 positivo)" };
			return groups;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada
				String[] groups = { "X�ok","15 kutank","45 kutank","3 po","6 po","K�ulyank 3 po","K�ulyank 6 po"};
				return groups;
			}//fin if
			else{
				//Si est� embarazada
				String[] groups = { "Xb�een, xb'een wa 3 po","Xkab� 3 po","Rox 3 po"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupQeqchi	
	
	/****** IDIOMA KAQCHIQUEL*****/
	
	private String[] getGroupKaqchiquel() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		  
		if(savedRadioIndex==1){
				//Si el diagnostico no lo sabe
			String[] groups = { "Xak 1", "Xak 2 (xak 1 jujik)", "Xak 3 (xak 2 jujik)" };
			return groups;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada
				String[] groups = { "tikirib�al","15 q�ij","45 q�ij","3 meses","6 meses","Cada 3 meses","Cada 6 meses"};
				return groups;
			}//fin if
			else{
				//Si est� embarazada
				String[] groups = { "nab�ey oxik�","rukab� oxik�","rox oxik�"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupKaqchiquel
	
/****** IDIOMA MAM*****/
	
	private String[] getGroupMam() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		  
		if(savedRadioIndex==1){
				//Si el diagnostico no lo sabe
			String[] groups = { "Paso 1", "Paso 2 (Si paso 1 es positivo)", "Paso 3 (Si paso 2 es positivo)" };
			return groups;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada
				String[] groups = { "Qe�ya","15 q�ij","45 q�ij","3 xjaw","6 xjaw","Cada 3 xjaw","Cada 6 xjaw"};
				return groups;
			}//fin if
			else{
				//Si est� embarazada
				String[] groups = { "Tnejel 3 xjaw","Tkab�an 3 xjaw","Toxan 3 xjaw"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupMam	
	
	/****** IDIOMA ESPA�OL*****/
	
	private String[] getGroupEspanol() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		  
		if(savedRadioIndex==1){
				//Si el diagnostico no lo sabe
			String[] groups = { "Paso 1", "Paso 2 (Si paso 1 es positivo)", "Paso 3 (Si paso 2 es positivo)" };
			return groups;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada
				String[] groups = { "Basal","15 d�as","45 d�as","3 meses","6 meses","Cada 3 meses","Cada 6 meses"};
				return groups;
			}//fin if
			else{
				//Si est� embarazada
				String[] groups = { "Primer Trimestre","Segundo Trimestre","Tercer Trimestre"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupEspa�ol
	
	/******************** FIN DE GRUPOS DE IDIOMAS *******************************************/
	
	/******************** CHILDREN DE IDIOMAS *******************************************/
	
	public String[][] getChildren(int idioma) {
		
		switch (idioma) {
        case 1:
        	return getChildrenKiche();
        case 2:
        	return getChildrenQeqchi();
        case 3:
        	return getChildrenKaqchiquel();        	
        case 4:
        	return getChildrenMam();          	
		default:
        	return getChildrenEspanol();
        }//fin switch
		

		//return null;
	}//fin getChildren

	private String[][] getChildrenKiche() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		if(savedRadioIndex==1){
			//Si el diagnostico no lo sabe
			String[][] children = {
	                {"Kak�amo prueba Elisa o Western Blot"},
	                {"Kak�amo prueba kaib'  (Prueba Kqajikib�a�)"},
	                {"Hemograma Tz�aqat","Kujunamaj c�lulas CD4","Prueba de carga viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Ch�ob�o s�ndrome de reconstituci�n inmune","Ch�ob�o psicol�gica","Ch�ob�o Kchakun social","Mulin ib� nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Ch�ob�o Yawab� chik","Papanicolau","Ch�ob�o oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","Chi�nik Pixb�anik anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Ch�ob�o s�ndrome de reconstituci�n inmune","Ch�ob�o nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Transaminasas","Chi�nik Pixb�anik anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Ch�ob�o s�ndrome de reconstituci�n inmune","Ch�ob�o psicol�gica","Ch�ob�o nutricional","Mulin ib� educador","Creatinina, Transaminasas"},
			                {"Ch�ob�o B�aqil","Ch�ob�or ITS","Mulin ib� nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas","Chi�nik de m�todos anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Mulin ib� psicol�gica","Ch�ob�o nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","Chi�nik Pixb�anik anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Mulin ib� Kchakun social","Ch�ob�o educador","Hematolog�a Kujunamaj plaquetas","Glucosa"},
			                {"Mulin ib� Kchakun social","Mulin ib� nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Ch�ob�o s�ndrome de reconstituci�n inmune","Ch�ob�o psicol�gica","Ch�ob�o Kchakun social","Mulin ib� nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Ch�ob�o oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","Chi�nik Pixb�anik anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Ch�ob�o s�ndrome de reconstituci�n inmune","Ch�ob�o nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Transaminasas","Chi�nik Pixb�anik anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Ch�ob�o s�ndrome de reconstituci�n inmune","Ch�ob�o psicol�gica","Ch�ob�o nutricional","Mulin ib� educador","Creatinina, Transaminasas"},
			                {"Ch�ob�o B�aqil","Ch�ob�or ITS","Mulin ib� nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas","Chi�nik de m�todos anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Mulin ib� psicol�gica","Ch�ob�o nutricional","Mulin ib� educador","Hematolog�a Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","CV4 y CD4","Chi�nik Pixb�anik anticonceptivos"},
			                {"Ch�ob�o B�aqil","Ch�ob�o ITS","Mulin ib� Kchakun social","Ch�ob�o educador","Hematolog�a Kujunamaj plaquetas","Glucosa"},
			                {"Mulin ib� Kchakun social","Mulin ib� nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si est� embarazada
				String[][] children = { {"Estatificaci�n Yab�il (CDC)", "Estatificaci�n Kujunamaj CD4 y CV","Ch�ob�o psicol�gico", "Ch�ob�o nutricional","Hematolog�a Tz�aqat","Ch�ob�o heces, orina, colesterol, trigic�ridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serolog�as toxoplasmosis y CMV", "Ultrasonograf�a","Ch�ob�o ginec�logo y papanicolaou"},
						{"Ultrasonograf�a","Kujunamaje linfocitos, qu�mica Ukik�el y glicemia","Ucholajil Kk�utun(ik) Ucholajil E�ajpajak�olib�al","Hematolog�a Tz�aqat", "Umajib�al de ARV","Ch�ob�o ginecol�gica","Ch�ob�o nutricional"},
						{"Ch�ob�o Utzil Kqumun(ik)","B�aqil (Diabetes / hipertensi�n)","Ch�ob�o ces�rea","Ch�ob�o ginecol�gica","Ch�ob�o nutricional","Pixab� Kuchajij Kqumun(ik)"}};
				return children;
			}//fin else				
		}//fin else
		//return null;
	}//fin getChildrenKiche

	private String[][] getChildrenQeqchi() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		if(savedRadioIndex==1){
			//Si el diagnostico no lo sabe
			String[][] children = {
	                {"Yalb�a�ix Elisa o Western Blot"},
	                {"B�aanunk xkab� Yalb�a�ix (Yalb�a�ix kawilal ch�ool)"},
	                {"Hemograma tz�aqal","Ajlank c�lulas CD4","Yalb�a�ix viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix Yajel xtib�e inmune","Yalb�a�ix psicol�gica","Yalb�a�ix de k�anjel social","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Yalb�a�ix yajaj ixq","Papanicolau","Yalb�a�ix oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix Yajel xtib�e inmune","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Transaminasas","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix Yajel xtib�e inmune","Yalb�a�ix psicol�gica","Yalb�a�ix nutricional","ch�utam aj k�utunel","Creatinina, Transaminasas"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Creatinina, Glucosa, Transaminasas","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix psicol�gica","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix de k�anjel social","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Glucosa"},
			                {"Yalb�a�ix k�anjel social","Yalb�a�ix nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix Yajel xtib�e inmune","Yalb�a�ix psicol�gica","Yalb�a�ix de k�anjel social","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Yalb�a�ix oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix Yajel xtib�e inmune","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Transaminasas","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix Yajel xtib�e inmune","Yalb�a�ix psicol�gica","Yalb�a�ix nutricional","ch�utam aj k�utunel","Creatinina, Transaminasas"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Creatinina, Glucosa, Transaminasas","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix psicol�gica","Yalb�a�ix nutricional","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","yeechi� b�aanunkil anticonceptivos"},
			                {"Yalb�a�ix f�sico","Yalb�a�ix ITS","Yalb�a�ix de k�anjel social","ch�utam aj k�utunel","Hematolog�a rik�in ajlank plaquetas","Glucosa"},
			                {"Yalb�a�ix k�anjel social","Yalb�a�ix nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si est� embarazada
				String[][] children = { {"Estatificaci�n yajel (par�metros CDC)", "Yalb�a�ix ajlank CD4 y CV","xjolominkil psicol�gico", "Yalb�a�ix nutricional","Hematolog�a tz�aqlok","Yalb�a�ix de heces, chu�, colesterol, trigic�ridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serolog�as toxoplasmosis y CMV", "Ultrasonograf�a","Yalb�a�ix ginecol�gica y papanicolaou"},
						{"Ultrasonograf�a","ajlank de linfocitos, qu�mica kik� y glicemia","k�uub� tzolb�a sexo seguro ut k�uub� familiar","Hematolog�a tz�aqlok", "X�ok de ARV","Yalb�a�ix ginecol�gica","Yalb�a�ix nutricional"},
						{"Yalb�a�ix de tuqtuukilal k�uula�al","Perfil biof�sico (Diabetes o hipertensi�n yajaj ixq)","Yalb�a�ix ces�rea","Yalb�a�ix ginecol�gica","Yalb�a�ix nutricional","inna�leb� ch�uukink k�uula�al"}};
				return children;
			}//fin else				
		}//fin else
		//return null;
	}//fin getChildrenQeqchi	
	
	private String[][] getChildrenKaqchiquel() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		if(savedRadioIndex==1){
			//Si el diagnostico no lo sabe
			String[][] children = {
	                {"tojtob�enik Elisa o Western Blot"},
	                {"b�an�k rukab� tojtob�enik (tojtob�enik confirmaci�n)"},
	                {"Hemograma tz�aqatel","ajilan�k rujotay k�aslemal CD4","tojtob�enik ejqan uchuq�a� itzelil"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
							{"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik yawab�il reconstituci�n inmune","tojtob�enik psicol�gica","tojtob�enik de samaj k�utsamaj","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","tojtob�enik ruyawa� ix�q","Papanicolau","tojtob�enik oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik yawab�il de reconstituci�n inmune","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Transaminasas","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik yawab�il de reconstituci�n inmune","tojtob�enik psicol�gica","tojtob�enik nutricional","peraj samaj con tijonela�","Creatinina, Transaminasas"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Creatinina, Glucosa, Transaminasas","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik psicol�gica","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik de samaj k�utsamaj","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Glucosa"},
			                {"tojtob�enik de samaj k�utsamaj","tojtob�enik nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
							{"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik yawab�il reconstituci�n inmune","tojtob�enik psicol�gica","tojtob�enik de samaj k�utsamaj","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","tojtob�enik oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik yawab�il de reconstituci�n inmune","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Transaminasas","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik yawab�il de reconstituci�n inmune","tojtob�enik psicol�gica","tojtob�enik nutricional","peraj samaj con tijonela�","Creatinina, Transaminasas"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Creatinina, Glucosa, Transaminasas","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik psicol�gica","tojtob�enik nutricional","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","k�utb��l na�oj b�eyalil anticonceptivos"},
			                {"tojtob�enik ch�akulaj","tojtob�enik ITS","tojtob�enik de samaj k�utsamaj","peraj samaj con tijonela�","Hematolog�a ajilan�k plaquetas","Glucosa"},
			                {"tojtob�enik de samaj k�utsamaj","tojtob�enik nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si est� embarazada
				String[][] children = { {"Yawab�il (par�metros CDC)", "ajilan�ks CD4 y CV","k�utel psicol�gico", "tojtob�enik nutricional","Hematolog�a tz�aqatel","tojtob�enik k�s, chulaj, saqb�uy�l q�anal, trigic�ridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serolog�as toxoplasmosis y CMV", "Ultrasonograf�a","tojtob�enik ginecol�gica y papanicolaou"},
						{"Ultrasonograf�a","ajilan�k de linfocitos, kimika� k�k� y glicemia","nuk�samaj tijonem pa kojolwin�q seguro, nuk�samaj ach�alalri��l","Hematolog�a tz�aqatel", "rutikirib�al ARV","tojtob�enik ginecol�gica","tojtob�enik nutricional"},
						{"tojtob�enik de utzil�j rutz�etik ak�walil","Perfil biof�sico (kab�yab�il, nimuchuq�ab ju�un�k ruyawa� ix�q)","k�utun�k ces�rea","tojtob�enik ginecol�gica","tojtob�enik nutricional","pixa� chajin ne�y"}};
				return children;
			}//fin else				
		}//fin else
		//return null;
	}//fin getChildrenKaqchiquel	
	
	private String[][] getChildrenMam() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		if(savedRadioIndex==1){
			//Si el diagnostico no lo sabe
			String[][] children = {
	                {"Prueba Elisa o Western Blot"},
	                {"Xi� Tkab�an prueba (Prueba de confirmaci�n)"},
	                {"Hemograma Tz�aqlixix","Ajlal de c�lulas CD4","Prueba Iqatz viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
							{"Examen f�sico","Evaluar y descartar ITS","Evaluar Yab�il de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n de aq�untl tnam","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Prueba wanaq","Papanicolau","Evaluaci�n oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar Yab�il de reconstituci�n inmune","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Transaminasas","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar Yab�il de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n nutricional","neq�chil xnaq�tzal","Creatinina, Transaminasas"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Creatinina, Glucosa, Transaminasas","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n psicol�gica","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n de aq�untl tnam","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Glucosa"},
			                {"Evaluaci�n de aq�untl tnam","Evaluaci�n nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
							{"Examen f�sico","Evaluar y descartar ITS","Evaluar Yab�il de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n de aq�untl tnam","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Evaluaci�n oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar Yab�il de reconstituci�n inmune","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Transaminasas","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar Yab�il de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n nutricional","neq�chil xnaq�tzal","Creatinina, Transaminasas"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Creatinina, Glucosa, Transaminasas","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n psicol�gica","Evaluaci�n nutricional","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","O�yil m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n de aq�untl tnam","neq�chil xnaq�tzal","Hematolog�a ajla�n de plaquetas","Glucosa"},
			                {"Evaluaci�n de aq�untl tnam","Evaluaci�n nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si est� embarazada
				String[][] children = { {"Estatificaci�n de la Yab�il (par�metros CDC)", "Estatificaci�n de recuentos de CD4 y CV","Yek�al psicol�gico", "Evaluaci�n nutricional","Hematolog�a tz�aqlixix","Ex�menes de heces, tz�a�lb�aj, colesterol, trigic�ridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serolog�as toxoplasmosis y CMV", "Ultrasonograf�a","Evaluaci�n ginecol�gica y papanicolaou"},
						{"Ultrasonograf�a","Ajlal linfocitos, chik�el y glicemia","Xnaq�tzb�il sexo seguro y planificaci�n familiar","Hematolog�a tz�aqlixix", "Qe�ya de ARV","Evaluaci�n ginecol�gica","Evaluaci�n nutricional"},
						{"Evaluaci�n de nakb�il okul","Perfil biof�sico (Diabetes o hipertensi�n inducida por el embarazo)","Programar ces�rea","Evaluaci�n ginecol�gica","Evaluaci�n nutricional","Nb�ib�ila xq�uqal okul"}};
				return children;
			}//fin else				
		}//fin else
		//return null;
	}//fin getChildrenMam	
	
	private String[][] getChildrenEspanol() {
		SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		int savedRadioIndex = sharedPreferences.getInt("diagnostico", 0);
		if(savedRadioIndex==1){
			//Si el diagnostico no lo sabe
			String[][] children = {
	                {"Prueba Elisa o Western Blot"},
	                {"Realizar segunda prueba (Prueba de confirmaci�n)"},
	                {"Hemograma completo","Recuento de c�lulas CD4","Test de carga viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no est� embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar s�ndrome de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n de trabajo social","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Prueba de embarazo","Papanicolau","Evaluaci�n oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar s�ndrome de reconstituci�n inmune","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Transaminasas","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar s�ndrome de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n nutricional","Sesi�n con educador","Creatinina, Transaminasas"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Creatinina, Glucosa, Transaminasas","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n psicol�gica","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","Papanicolau","CV4 y CD4","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n de trabajo social","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Glucosa"},
			                {"Evaluaci�n de trabajo social","Evaluaci�n nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar s�ndrome de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n de trabajo social","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","VDRL o RPR, HBsAG, Ac HCV","Evaluaci�n oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX T�rax","CV4 y CD4","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar s�ndrome de reconstituci�n inmune","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Transaminasas","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluar s�ndrome de reconstituci�n inmune","Evaluaci�n psicol�gica","Evaluaci�n nutricional","Sesi�n con educador","Creatinina, Transaminasas"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Creatinina, Glucosa, Transaminasas","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n psicol�gica","Evaluaci�n nutricional","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lip�dico","CV4 y CD4","Oferta de m�todos anticonceptivos"},
			                {"Examen f�sico","Evaluar y descartar ITS","Evaluaci�n de trabajo social","Sesi�n con educador","Hematolog�a con recuento de plaquetas","Glucosa"},
			                {"Evaluaci�n de trabajo social","Evaluaci�n nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si est� embarazada
				String[][] children = { {"Estatificaci�n de la enfermedad (par�metros CDC)", "Estatificaci�n de recuentos de CD4 y CV","Asesoramiento psicol�gico", "Evaluaci�n nutricional","Hematolog�a completa","Ex�menes de heces, orina, colesterol, trigic�ridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serolog�as toxoplasmosis y CMV", "Ultrasonograf�a","Evaluaci�n ginecol�gica y papanicolaou"},
						{"Ultrasonograf�a","Recuento de linfocitos, qu�mica sangu�nea y glicemia","Plan educacional sobre sexo seguro y planificaci�n familiar","Hematolog�a completa", "Inicio de ARV","Evaluaci�n ginecol�gica","Evaluaci�n nutricional"},
						{"Evaluaci�n de bienestar fetal","Perfil biof�sico (Diabetes o hipertensi�n inducida por el embarazo)","Programar ces�rea","Evaluaci�n ginecol�gica","Evaluaci�n nutricional","Consejer�a en cuidado del beb�"}};
				return children;
			}//fin else				
		}//fin else
		//return null;
	}//fin getChildrenEspa�ol
	
	

	/******************** FIN DE CHILDREN DE IDIOMAS *******************************************/	
	
	public static void reiniciarActivity(ExpandableListActivity actividad){
        Intent intent=new Intent();
        intent.setClass(actividad, actividad.getClass());
        //llamamos a la actividad
        actividad.startActivity(intent);
        //finalizamos la actividad actual
        actividad.finish();
	}//fin reiniciarActivity
	
	private void SavePreferences(String key, int value){
		/******* IDIOMAS VALUES ***********
		 * Espa�ol=0
		 * K'iche'=1
		 * Q'eqchi'=2
		 * Kaqchiquel=3
		 * Mam=4
		 * */
		
		  SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
		  SharedPreferences.Editor editor = sharedPreferences.edit();
		  editor.putInt(key, value);
		  editor.commit(); 
	}//fin SavePreferences
/*
    @Override
    public void onBackPressed() {

    }		*/
	
}//fin clase Principal
