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
    	    menu.add(Menu.NONE, MNU_OPC3, Menu.NONE, "Localizador de clínicas")
    	            .setIcon(android.R.drawable.ic_menu_mapmode);
    	    SubMenu smnu = menu.
    	    	    addSubMenu(Menu.NONE, MNU_OPC4, Menu.NONE, "Cambiar Idioma")
    	    	        .setIcon(android.R.drawable.ic_menu_set_as);
    	    	smnu.add(Menu.NONE, SMNU_OPC1, Menu.NONE, "Español");
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
            	Toast.makeText(this, "Cambiado a Español",
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
            	Toast.makeText(this, "Ank’al Mam'",
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
				//Si no está embarazada
				String[] groups = { "Uchapale'm","15 Q'ij","45 Q'ij","3 Ik’","6 Ik’","Uterne'xik 3 Ik’","Uterne'xik 6 Ik’"};
				return groups;
			}//fin if
			else{
				//Si está embarazada
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
			String[] groups = { "B’eeresinkil 1", "B’eeresinkil 2 (B’eeresinkil 1 positivo)", "B’eeresinkil 3 (B’eeresinkil 2 positivo)" };
			return groups;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no está embarazada
				String[] groups = { "X’ok","15 kutank","45 kutank","3 po","6 po","K’ulyank 3 po","K’ulyank 6 po"};
				return groups;
			}//fin if
			else{
				//Si está embarazada
				String[] groups = { "Xb’een, xb'een wa 3 po","Xkab’ 3 po","Rox 3 po"};
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
				//Si no está embarazada
				String[] groups = { "tikirib’al","15 q’ij","45 q’ij","3 meses","6 meses","Cada 3 meses","Cada 6 meses"};
				return groups;
			}//fin if
			else{
				//Si está embarazada
				String[] groups = { "nab’ey oxik’","rukab’ oxik’","rox oxik’"};
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
				//Si no está embarazada
				String[] groups = { "Qe’ya","15 q’ij","45 q’ij","3 xjaw","6 xjaw","Cada 3 xjaw","Cada 6 xjaw"};
				return groups;
			}//fin if
			else{
				//Si está embarazada
				String[] groups = { "Tnejel 3 xjaw","Tkab’an 3 xjaw","Toxan 3 xjaw"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupMam	
	
	/****** IDIOMA ESPAÑOL*****/
	
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
				//Si no está embarazada
				String[] groups = { "Basal","15 días","45 días","3 meses","6 meses","Cada 3 meses","Cada 6 meses"};
				return groups;
			}//fin if
			else{
				//Si está embarazada
				String[] groups = { "Primer Trimestre","Segundo Trimestre","Tercer Trimestre"};
				return groups;
			}//fin else			
		}//fin else
		//return null;
	}//fin getGroupEspañol
	
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
	                {"Kak’amo prueba Elisa o Western Blot"},
	                {"Kak’amo prueba kaib'  (Prueba Kqajikib’a’)"},
	                {"Hemograma Tz’aqat","Kujunamaj células CD4","Prueba de carga viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no está embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Ch’ob’o síndrome de reconstitución inmune","Ch’ob’o psicológica","Ch’ob’o Kchakun social","Mulin ib’ nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Ch’ob’o Yawab’ chik","Papanicolau","Ch’ob’o oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","Chi’nik Pixb’anik anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Ch’ob’o síndrome de reconstitución inmune","Ch’ob’o nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Transaminasas","Chi’nik Pixb’anik anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Ch’ob’o síndrome de reconstitución inmune","Ch’ob’o psicológica","Ch’ob’o nutricional","Mulin ib’ educador","Creatinina, Transaminasas"},
			                {"Ch’ob’o B’aqil","Ch’ob’or ITS","Mulin ib’ nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas","Chi’nik de métodos anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Mulin ib’ psicológica","Ch’ob’o nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","Chi’nik Pixb’anik anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Mulin ib’ Kchakun social","Ch’ob’o educador","Hematología Kujunamaj plaquetas","Glucosa"},
			                {"Mulin ib’ Kchakun social","Mulin ib’ nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Ch’ob’o síndrome de reconstitución inmune","Ch’ob’o psicológica","Ch’ob’o Kchakun social","Mulin ib’ nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Ch’ob’o oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","Chi’nik Pixb’anik anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Ch’ob’o síndrome de reconstitución inmune","Ch’ob’o nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Transaminasas","Chi’nik Pixb’anik anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Ch’ob’o síndrome de reconstitución inmune","Ch’ob’o psicológica","Ch’ob’o nutricional","Mulin ib’ educador","Creatinina, Transaminasas"},
			                {"Ch’ob’o B’aqil","Ch’ob’or ITS","Mulin ib’ nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas","Chi’nik de métodos anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Mulin ib’ psicológica","Ch’ob’o nutricional","Mulin ib’ educador","Hematología Kujunamaj plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","CV4 y CD4","Chi’nik Pixb’anik anticonceptivos"},
			                {"Ch’ob’o B’aqil","Ch’ob’o ITS","Mulin ib’ Kchakun social","Ch’ob’o educador","Hematología Kujunamaj plaquetas","Glucosa"},
			                {"Mulin ib’ Kchakun social","Mulin ib’ nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si está embarazada
				String[][] children = { {"Estatificación Yab’il (CDC)", "Estatificación Kujunamaj CD4 y CV","Ch’ob’o psicológico", "Ch’ob’o nutricional","Hematología Tz’aqat","Ch’ob’o heces, orina, colesterol, trigicéridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serologías toxoplasmosis y CMV", "Ultrasonografía","Ch’ob’o ginecólogo y papanicolaou"},
						{"Ultrasonografía","Kujunamaje linfocitos, química Ukik’el y glicemia","Ucholajil Kk’utun(ik) Ucholajil E’ajpajak’olib’al","Hematología Tz’aqat", "Umajib’al de ARV","Ch’ob’o ginecológica","Ch’ob’o nutricional"},
						{"Ch’ob’o Utzil Kqumun(ik)","B’aqil (Diabetes / hipertensión)","Ch’ob’o cesárea","Ch’ob’o ginecológica","Ch’ob’o nutricional","Pixab’ Kuchajij Kqumun(ik)"}};
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
	                {"Yalb’a’ix Elisa o Western Blot"},
	                {"B’aanunk xkab’ Yalb’a’ix (Yalb’a’ix kawilal ch’ool)"},
	                {"Hemograma tz’aqal","Ajlank células CD4","Yalb’a’ix viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no está embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix Yajel xtib’e inmune","Yalb’a’ix psicológica","Yalb’a’ix de k’anjel social","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Yalb’a’ix yajaj ixq","Papanicolau","Yalb’a’ix oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix Yajel xtib’e inmune","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Transaminasas","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix Yajel xtib’e inmune","Yalb’a’ix psicológica","Yalb’a’ix nutricional","ch’utam aj k’utunel","Creatinina, Transaminasas"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Creatinina, Glucosa, Transaminasas","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix psicológica","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix de k’anjel social","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Glucosa"},
			                {"Yalb’a’ix k’anjel social","Yalb’a’ix nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix Yajel xtib’e inmune","Yalb’a’ix psicológica","Yalb’a’ix de k’anjel social","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Yalb’a’ix oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix Yajel xtib’e inmune","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Transaminasas","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix Yajel xtib’e inmune","Yalb’a’ix psicológica","Yalb’a’ix nutricional","ch’utam aj k’utunel","Creatinina, Transaminasas"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Creatinina, Glucosa, Transaminasas","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix psicológica","Yalb’a’ix nutricional","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","yeechi’ b’aanunkil anticonceptivos"},
			                {"Yalb’a’ix físico","Yalb’a’ix ITS","Yalb’a’ix de k’anjel social","ch’utam aj k’utunel","Hematología rik’in ajlank plaquetas","Glucosa"},
			                {"Yalb’a’ix k’anjel social","Yalb’a’ix nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si está embarazada
				String[][] children = { {"Estatificación yajel (parámetros CDC)", "Yalb’a’ix ajlank CD4 y CV","xjolominkil psicológico", "Yalb’a’ix nutricional","Hematología tz’aqlok","Yalb’a’ix de heces, chu’, colesterol, trigicéridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serologías toxoplasmosis y CMV", "Ultrasonografía","Yalb’a’ix ginecológica y papanicolaou"},
						{"Ultrasonografía","ajlank de linfocitos, química kik’ y glicemia","k’uub’ tzolb’a sexo seguro ut k’uub’ familiar","Hematología tz’aqlok", "X’ok de ARV","Yalb’a’ix ginecológica","Yalb’a’ix nutricional"},
						{"Yalb’a’ix de tuqtuukilal k’uula’al","Perfil biofísico (Diabetes o hipertensión yajaj ixq)","Yalb’a’ix cesárea","Yalb’a’ix ginecológica","Yalb’a’ix nutricional","inna’leb’ ch’uukink k’uula’al"}};
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
	                {"tojtob’enik Elisa o Western Blot"},
	                {"b’anïk rukab’ tojtob’enik (tojtob’enik confirmación)"},
	                {"Hemograma tz’aqatel","ajilanïk rujotay k’aslemal CD4","tojtob’enik ejqan uchuq’a’ itzelil"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no está embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
							{"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik yawab’il reconstitución inmune","tojtob’enik psicológica","tojtob’enik de samaj k’utsamaj","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","tojtob’enik ruyawa’ ixöq","Papanicolau","tojtob’enik oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik yawab’il de reconstitución inmune","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Transaminasas","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik yawab’il de reconstitución inmune","tojtob’enik psicológica","tojtob’enik nutricional","peraj samaj con tijonela’","Creatinina, Transaminasas"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Creatinina, Glucosa, Transaminasas","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik psicológica","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik de samaj k’utsamaj","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Glucosa"},
			                {"tojtob’enik de samaj k’utsamaj","tojtob’enik nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
							{"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik yawab’il reconstitución inmune","tojtob’enik psicológica","tojtob’enik de samaj k’utsamaj","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","tojtob’enik oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik yawab’il de reconstitución inmune","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Transaminasas","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik yawab’il de reconstitución inmune","tojtob’enik psicológica","tojtob’enik nutricional","peraj samaj con tijonela’","Creatinina, Transaminasas"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Creatinina, Glucosa, Transaminasas","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik psicológica","tojtob’enik nutricional","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","k’utb’äl na’oj b’eyalil anticonceptivos"},
			                {"tojtob’enik ch’akulaj","tojtob’enik ITS","tojtob’enik de samaj k’utsamaj","peraj samaj con tijonela’","Hematología ajilanïk plaquetas","Glucosa"},
			                {"tojtob’enik de samaj k’utsamaj","tojtob’enik nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si está embarazada
				String[][] children = { {"Yawab’il (parámetros CDC)", "ajilanïks CD4 y CV","k’utel psicológico", "tojtob’enik nutricional","Hematología tz’aqatel","tojtob’enik kïs, chulaj, saqb’uyül q’anal, trigicéridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serologías toxoplasmosis y CMV", "Ultrasonografía","tojtob’enik ginecológica y papanicolaou"},
						{"Ultrasonografía","ajilanïk de linfocitos, kimika’ kïk’ y glicemia","nuk’samaj tijonem pa kojolwinäq seguro, nuk’samaj ach’alalri’ïl","Hematología tz’aqatel", "rutikirib’al ARV","tojtob’enik ginecológica","tojtob’enik nutricional"},
						{"tojtob’enik de utziläj rutz’etik ak’walil","Perfil biofísico (kab’yab’il, nimuchuq’ab ju’unïk ruyawa’ ixöq)","k’utunïk cesárea","tojtob’enik ginecológica","tojtob’enik nutricional","pixa’ chajin ne’y"}};
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
	                {"Xi’ Tkab’an prueba (Prueba de confirmación)"},
	                {"Hemograma Tz’aqlixix","Ajlal de células CD4","Prueba Iqatz viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no está embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
							{"Examen físico","Evaluar y descartar ITS","Evaluar Yab’il de reconstitución inmune","Evaluación psicológica","Evaluación de aq’untl tnam","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Prueba wanaq","Papanicolau","Evaluación oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar Yab’il de reconstitución inmune","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Transaminasas","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar Yab’il de reconstitución inmune","Evaluación psicológica","Evaluación nutricional","neq’chil xnaq’tzal","Creatinina, Transaminasas"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Creatinina, Glucosa, Transaminasas","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación psicológica","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación de aq’untl tnam","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Glucosa"},
			                {"Evaluación de aq’untl tnam","Evaluación nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
							{"Examen físico","Evaluar y descartar ITS","Evaluar Yab’il de reconstitución inmune","Evaluación psicológica","Evaluación de aq’untl tnam","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Evaluación oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar Yab’il de reconstitución inmune","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Transaminasas","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar Yab’il de reconstitución inmune","Evaluación psicológica","Evaluación nutricional","neq’chil xnaq’tzal","Creatinina, Transaminasas"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Creatinina, Glucosa, Transaminasas","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación psicológica","Evaluación nutricional","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","O’yil métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación de aq’untl tnam","neq’chil xnaq’tzal","Hematología ajla’n de plaquetas","Glucosa"},
			                {"Evaluación de aq’untl tnam","Evaluación nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si está embarazada
				String[][] children = { {"Estatificación de la Yab’il (parámetros CDC)", "Estatificación de recuentos de CD4 y CV","Yek’al psicológico", "Evaluación nutricional","Hematología tz’aqlixix","Exámenes de heces, tz’a’lb’aj, colesterol, trigicéridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serologías toxoplasmosis y CMV", "Ultrasonografía","Evaluación ginecológica y papanicolaou"},
						{"Ultrasonografía","Ajlal linfocitos, chik’el y glicemia","Xnaq’tzb’il sexo seguro y planificación familiar","Hematología tz’aqlixix", "Qe’ya de ARV","Evaluación ginecológica","Evaluación nutricional"},
						{"Evaluación de nakb’il okul","Perfil biofísico (Diabetes o hipertensión inducida por el embarazo)","Programar cesárea","Evaluación ginecológica","Evaluación nutricional","Nb’ib’ila xq’uqal okul"}};
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
	                {"Realizar segunda prueba (Prueba de confirmación)"},
	                {"Hemograma completo","Recuento de células CD4","Test de carga viral"}};
			return children;
		}//fin if
		else{
			//Si el diagnostico es positivo
			
			sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
			savedRadioIndex = sharedPreferences.getInt("embarazada", 0);
			
			if(savedRadioIndex==1){
				//Si no está embarazada	
				
				sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
				savedRadioIndex = sharedPreferences.getInt("genero", 0);
				if(savedRadioIndex==1){
					//Si es mujer
					String[][] children = {
			                {"Examen físico","Evaluar y descartar ITS","Evaluar síndrome de reconstitución inmune","Evaluación psicológica","Evaluación de trabajo social","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Prueba de embarazo","Papanicolau","Evaluación oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar síndrome de reconstitución inmune","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Transaminasas","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar síndrome de reconstitución inmune","Evaluación psicológica","Evaluación nutricional","Sesión con educador","Creatinina, Transaminasas"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Creatinina, Glucosa, Transaminasas","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación psicológica","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","Papanicolau","CV4 y CD4","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación de trabajo social","Sesión con educador","Hematología con recuento de plaquetas","Glucosa"},
			                {"Evaluación de trabajo social","Evaluación nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","Papanicolau","CV4 y CD4"}};
					return children;
				}//fin if
				else{
					//Si es hombre
					String[][] children = {
			                {"Examen físico","Evaluar y descartar ITS","Evaluar síndrome de reconstitución inmune","Evaluación psicológica","Evaluación de trabajo social","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","VDRL o RPR, HBsAG, Ac HCV","Evaluación oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)","RX Tórax","CV4 y CD4","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar síndrome de reconstitución inmune","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Transaminasas","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluar síndrome de reconstitución inmune","Evaluación psicológica","Evaluación nutricional","Sesión con educador","Creatinina, Transaminasas"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Creatinina, Glucosa, Transaminasas","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación psicológica","Evaluación nutricional","Sesión con educador","Hematología con recuento de plaquetas","Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina","Perfil lipídico","CV4 y CD4","Oferta de métodos anticonceptivos"},
			                {"Examen físico","Evaluar y descartar ITS","Evaluación de trabajo social","Sesión con educador","Hematología con recuento de plaquetas","Glucosa"},
			                {"Evaluación de trabajo social","Evaluación nutricional","Creatinina, Transaminasas, DHL, Fosfatasa alcalina","CV4 y CD4"}};
					return children;					
				}//fin else
			}//fin if
			else{
				//Si está embarazada
				String[][] children = { {"Estatificación de la enfermedad (parámetros CDC)", "Estatificación de recuentos de CD4 y CV","Asesoramiento psicológico", "Evaluación nutricional","Hematología completa","Exámenes de heces, orina, colesterol, trigicéridos, creatinina, glicemia","VDRL, HBsAG, RPR, VHC, PPD, serologías toxoplasmosis y CMV", "Ultrasonografía","Evaluación ginecológica y papanicolaou"},
						{"Ultrasonografía","Recuento de linfocitos, química sanguínea y glicemia","Plan educacional sobre sexo seguro y planificación familiar","Hematología completa", "Inicio de ARV","Evaluación ginecológica","Evaluación nutricional"},
						{"Evaluación de bienestar fetal","Perfil biofísico (Diabetes o hipertensión inducida por el embarazo)","Programar cesárea","Evaluación ginecológica","Evaluación nutricional","Consejería en cuidado del bebé"}};
				return children;
			}//fin else				
		}//fin else
		//return null;
	}//fin getChildrenEspañol
	
	

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
		 * Español=0
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
