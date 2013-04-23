package com.outline.org.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static Utils instance;
	
	private static String PATTERN_DATE_DEFAULT = "dd/MM/yyyy";
	
	public static Utils getInstance() {
		if (instance == null){
			instance = new Utils();
		}
		return instance;
	}
	
	public String[] quebrarDataEmPartes(String data) {		
		return quebrarDataEmPartes(data, null);
	}
	
	public String[] quebrarDataEmPartes(String data, String separador) {		
		String[] dtPartes = null;
		
		if (separador == null){
			separador = "/";
		}
		
		if (data != null){			
			dtPartes = data.split(separador);
		}
		
		return dtPartes;
	}

	public String transformarDateToString(Date data) {
		return transformarDateToString(data, null);
	}
	
	public String transformarDateToString(Date data, String formato) {
		if (formato == null) {
			formato = PATTERN_DATE_DEFAULT;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(data);
	}
}
