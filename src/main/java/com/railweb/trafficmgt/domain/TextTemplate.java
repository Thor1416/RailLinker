package com.railweb.trafficmgt.domain;

import java.util.Locale;
import java.util.Map;

import com.railweb.trafficmgt.domain.train.Train;

public abstract class TextTemplate {

	public enum Language{
		GROOVY, MVEL, PLAIN, XSL;
	}
	
	private String template;
	
	protected TextTemplate(String template){
		
	}
	
	public static Map<String, Object> getBinding(Train train, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	public String evaluate(Map<String, Object> binding) {
		// TODO Auto-generated method stub
		return null;
	}

}
