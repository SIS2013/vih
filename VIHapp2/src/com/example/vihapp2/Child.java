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
		if(child.equals("Examen físico")){
			return "Examen del cuerpo para determinar problemas físicos";
		}//fin if
		else if(child.equals("Evaluar y descartar ITS")){
			return "Evaluación de otras enfermedades ITS (\"Enferfemades de Transmisión Sexual\")";
		}//fin else if
		else if(child.equals("Evaluar síndrome de reconstitución inmune")){
			return "El síndrome (SRI) conduce a una recrudescencia de " +
					"infecciones que ya se encontraban presentes en el huésped y que se ven " +
					"exacerbados por la restauración del sistema inmune.";
		}//fin else if
		else if(child.equals("Evaluación psicológica")||child.equals("Asesoramiento psicológico")){
			return "Evaluación de situación psicológica del paciente respecto a su salud mental " +
					"derivado de conocer su infección de VIH.";
		}//fin else if
		else if(child.equals("Evaluación de trabajo social"))
			return "Identificación del problema en la comunidad donde vive el paciente.";
		else if(child.equals("Evaluación nutricional")){
			return "Establecer programas de dietas y control de alimentación";
		}//fin else if
		else if(child.equals("Sesión con educador")||child.equals("Plan educacional sobre sexo seguro y planificación familiar")){
			return "Sesiones de orientación para saber cómo sobrellevar la enfermedad";
		}//fin else if
		else if(child.equals("Hematología con recuento de plaquetas")){
			return "Examen de recuento de plaquetas en sangre.";
		}//fin else if
		else if (child.equals("Creatinina, Glucosa, Transaminasas, DHL, Fosfatasa alcalina")||child.equals("VDRL o RPR, HBsAG, Ac HCV")){
			return "Exámenes de sangre adicionales determinantes";
		}//fin else if
		else if (child.equals("Perfil lipídico")){
			return "Exámenes diagnósticos de laboratorio clínico, solicitados " +
					"para determinar el estado del metabolismo de los lípidos corporales, " +
					"comúnmente en suero sanguíneo";
		}//fin else if
		else if(child.equals("Prueba de embarazo")){
			return "Evaluación para determinar si la paciente está embarazada";
		}//fin else if
		else if (child.equals("Papanicolau")||child.equals("Evaluación ginecológica y papanicolaou")||child.equals("Evaluación ginecológica")){
			return "El objetivo de esta prueba consiste en encontrar los cambios de las células " +
					"del cuello uterino que son precursoras del cáncer";
		}//fin else if
		else if (child.equals("Evaluación oftalmológica (Considerar si conteo de CD4 es menor de 100 cel/ml)")){
			return "Prueba de visión, considerado de acuerdo a células bajas de carga viral. \n" +
					"La visión puede verse afectada en pacientes con VIH";
		}//fin else if
		else if(child.equals("RX Tórax")){
			return "Radiografía de tórax";
		}//fin else if
		else if (child.equals("CV4 y CD4")||child.equals("Estatificación de recuentos de CD4 y CV")||child.equals("Recuento de células CD4")||child.equals("Test de carga viral")){
			return "Prueba de recuento de células de carga viral";
		}//fin else if
		else if(child.equals("Oferta de métodos anticonceptivos")){
			return "Establecimiento de plan de prevención de embarazos en pacientes infectados";
		}//fin else if
		else if(child.equals("Estatificación de la enfermedad (parámetros CDC)")){
			return "Los CDC es una clasificación de la infección por VIH-1 y la definición de caso de SIDA en " +
					"adultos y en niños menores de 13 años";
		}//fin else if
		else if (child.equals("Hematología completa")||child.equals("Hemograma completo")){
			return "Estudio e investigación de la sangre y los órganos hematopoyéticos " +
					"(médula ósea, ganglios linfáticos, bazo, etc)";
		}//fin else if
		else if (child.equals("Ultrasonografía")||child.equals("Evaluación de bienestar fetal")){
			return "Evaluación de situación actual del bebé";
		}//fin else if
		else if (child.equals("Perfil biofísico (Diabetes o hipertensión inducida por el embarazo)")){
			return "Investigación de otras enfermedades producidas por el embarazo" +
					" para un mejor tratamiento de enfermedad VIH";
		}//fin else if
		else if (child.equals("Consejería en cuidado del bebé")){
			return "Asesoría en cuidados del bebé luego del nacimiento, estableciendo controles" +
					" para asegurar el estado del bebé";
		}//fin else if
		else if (child.equals("Programar cesárea")){
			return "Calendarización para el nacimiento del bebé";
		}//fin else if
		else if (child.equals("Inicio de ARV")){
			return "Inicio del tratamiento antirretroviral. Los fármacos antirretrovirales " +
					"o antirretrovíricos1 son medicamentos antivirales específicos para el " +
					"tratamiento de infecciones por retrovirus como el VIH";
		}//fin else if
		else if (child.equals("Prueba Elisa o Western Blot")){
			return "Una prueba ELISA positiva siempre va seguida de una inmunotransferencia " +
					"(Western blot) que, de ser también positiva, confirma una infección por VIH";
		}//fin else if
		else if (child.equals("Realizar segunda prueba (Prueba de confirmación)")){
			return "Prueba extra de confirmación de diagnóstico";
		}//fin else if		
		else{
			return  "Exámenes adicionales determinantes";
		}//fin else
	}//fin getDetalleEspanol
}
