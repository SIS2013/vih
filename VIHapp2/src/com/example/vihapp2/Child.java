package com.example.vihapp2;

public class Child {
	
	
	public static String getDetalleChildren(String child, int idioma){
		String detalle = "";
		
		switch (idioma) {
//        case 1:
//        	return getDetalleKiche();
//        case 2:
//        	return getDetalleQeqchi();  
//        case 3:
//        	return getDetalleKaqchiquel();      
//        case 4:
//        	return getDetalleMam();         	
		default:
        	detalle= getDetalleEspanol(child);
        }//fin switch
		
		return detalle;		
	}//fin getDetailChildren
	String[] children = {};	
	
	public static String getDetalleEspanol(String child){
		if(child.equals("Examen f�sico")){
			return "Examen del cuerpo para determinar problemas f�sicos";
		}//fin if
		else if(child.equals("Evaluar y descartar ITS")){
			return "Evaluaci�n de otras enfermedades ITS (\"Enferfemades de Transmisi�n Sexual\")";
		}//fin else if
		else if(child.equals("Evaluar s�ndrome de reconstituci�n inmune")){
			return "El s�ndrome (SRI) conduce a una recrudescencia de " +
					"infecciones que ya se encontraban presentes en el hu�sped y que se ven " +
					"exacerbados por la restauraci�n del sistema inmune.";
		}//fin else if
		else if(child.equals("Evaluaci�n psicol�gica")||child.equals("Asesoramiento psicol�gico")){
			return "Evaluaci�n de situaci�n psicol�gica del paciente respecto a su salud mental " +
					"derivado de conocer su infecci�n de VIH.";
		}//fin else if
		else if(child.equals("Evaluaci�n de trabajo social"))
			return "Identificaci�n del problema en la comunidad donde vive el paciente.";
		else if(child.equals("Evaluaci�n nutricional")){
			return "Establecer programas de dietas y control de alimentaci�n";
		}//fin else if
		else if(child.equals("Sesi�n con educador")||child.equals("Plan educacional sobre sexo seguro y planificaci�n familiar")){
			return "Sesiones de orientaci�n para saber c�mo sobrellevar la enfermedad";
		}//fin else if
		else if(child.equals("Hematolog�a con recuento de plaquetas")){
			return "Examen de recuento de plaquetas en sangre.";
		}//fin else if
		else if (child.equals("Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina")||child.equals("VDRL o RPR, HBsAG, Ac HCV")){
			return "Ex�menes de sangre adicionales determinantes";
		}//fin else if
		else if (child.equals("Perfil lip�dico")){
			return "Ex�menes diagn�sticos de laboratorio cl�nico, solicitados " +
					"para determinar el estado del metabolismo de los l�pidos corporales, " +
					"com�nmente en suero sangu�neo";
		}//fin else if
		else if(child.equals("Prueba de embarazo")){
			return "Evaluaci�n para determinar si la paciente est� embarazada";
		}//fin else if
		else if (child.equals("Papanicolau")||child.equals("Evaluaci�n ginecol�gica y papanicolaou")||child.equals("Evaluaci�n ginecol�gica")){
			return "El objetivo de esta prueba consiste en encontrar los cambios de las c�lulas " +
					"del cuello uterino que son precursoras del c�ncer";
		}//fin else if
		else if (child.equals("Evaluaci�n oftalmol�gica (Considerar si conteo de CD4 es menor de 100 cel/ml)")){
			return "Prueba de visi�n, considerado de acuerdo a c�lulas bajas de carga viral. \n" +
					"La visi�n puede verse afectada en pacientes con VIH";
		}//fin else if
		else if(child.equals("RX T�rax")){
			return "Radiograf�a de t�rax";
		}//fin else if
		else if (child.equals("CV4 y CD4")||child.equals("Estatificaci�n de recuentos de CD4 y CV")||child.equals("Recuento de c�lulas CD4")||child.equals("Test de carga viral")){
			return "Prueba de recuento de c�lulas de carga viral";
		}//fin else if
		else if(child.equals("Oferta de m�todos anticonceptivos")){
			return "Establecimiento de plan de prevenci�n de embarazos en pacientes infectados";
		}//fin else if
		else if(child.equals("Estatificaci�n de la enfermedad (par�metros CDC)")){
			return "Los CDC es una clasificaci�n de la infecci�n por VIH-1 y la definici�n de caso de SIDA en " +
					"adultos y en ni�os menores de 13 a�os";
		}//fin else if
		else if (child.equals("Hematolog�a completa")||child.equals("Hemograma completo")){
			return "Estudio e investigaci�n de la sangre y los �rganos hematopoy�ticos " +
					"(m�dula �sea, ganglios linf�ticos, bazo, etc)";
		}//fin else if
		else if (child.equals("Ultrasonograf�a")||child.equals("Evaluaci�n de bienestar fetal")){
			return "Evaluaci�n de situaci�n actual del beb�";
		}//fin else if
		else if (child.equals("Perfil biof�sico (Diabetes o hipertensi�n inducida por el embarazo)")){
			return "Investigaci�n de otras enfermedades producidas por el embarazo" +
					" para un mejor tratamiento de enfermedad VIH";
		}//fin else if
		else if (child.equals("Consejer�a en cuidado del beb�")){
			return "Asesor�a en cuidados del beb� luego del nacimiento, estableciendo controles" +
					" para asegurar el estado del beb�";
		}//fin else if
		else if (child.equals("Programar ces�rea")){
			return "Calendarizaci�n para el nacimiento del beb�";
		}//fin else if
		else if (child.equals("Inicio de ARV")){
			return "Inicio del tratamiento antirretroviral. Los f�rmacos antirretrovirales " +
					"o antirretrov�ricos1 son medicamentos antivirales espec�ficos para el " +
					"tratamiento de infecciones por retrovirus como el VIH";
		}//fin else if
		else if (child.equals("Prueba Elisa o Western Blot")){
			return "Una prueba ELISA positiva siempre va seguida de una inmunotransferencia " +
					"(Western blot) que, de ser tambi�n positiva, confirma una infecci�n por VIH";
		}//fin else if
		else if (child.equals("Realizar segunda prueba (Prueba de confirmaci�n)")){
			return "Prueba extra de confirmaci�n de diagn�stico";
		}//fin else if		
		else{
			return  "Ex�menes adicionales determinantes";
		}//fin else
	}//fin getDetalleEspanol
}
