package com.outline.org.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	public Timestamp converterData(String data_) {
        String dia  = null;
        String mes  = null;
        String ano  = null;
        String hora = null;
        String min  = null;
        String sec  = null;
        Timestamp ret = null;        

        if (data_ != null) {
        	if (data_.indexOf("-") != -1) {
        		ano = data_.substring(0,4);                
                mes = data_.substring(5,7);
                dia = data_.substring(8,10);
                data_ = ano+"-"+mes+"-"+dia+" 00:00:00.000000000";
                ret = Timestamp.valueOf(data_);        		
        	} else if (data_.indexOf("/") != -1) {
        		dia = data_.substring(0,2);
                mes = data_.substring(3,5);
                ano = data_.substring(6,10);
                
                hora = data_.substring(11,13);
                min  = data_.substring(14,16);
                sec  = data_.substring(17,data_.length());
                
                data_ = ano+"-"+mes+"-"+dia+" "+hora+":"+min+":"+sec+".000000000";
                ret = Timestamp.valueOf(data_);
        	}
        }
        
        return ret;
    }
	
	public Timestamp converterDataToDate(String data_) {        
        Date dt = null; 
        Timestamp ret = null;

        if (data_ != null) {
        	String[] arrData = data_.split("/");
        	
        	Calendar c = Calendar.getInstance();
    		c.set(Calendar.DAY_OF_MONTH, 	Integer.parseInt(arrData[0]));
    		c.set(Calendar.MONTH, 			Integer.parseInt(arrData[1])-1);
    		c.set(Calendar.YEAR,  			Integer.parseInt(arrData[2]));    		
    		
    		//se tiver hora/min/seg
    		String[] arrHora = data_.split(":");
    		
    		if (data_.indexOf(":") != -1 && (arrHora != null && arrHora.length > 0)) {    			
    			c.set(Calendar.HOUR, 	Integer.parseInt(arrHora[0]));
    			c.set(Calendar.MINUTE, 	Integer.parseInt(arrHora[1]));
    			c.set(Calendar.SECOND, 	Integer.parseInt(arrHora[2]));
    		}
        	
    		dt =  new java.sql.Date(c.getTime().getTime());
    		ret = new Timestamp(dt.getTime());
        }        
        
        return ret;
    }
}
