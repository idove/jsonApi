package com.idove.utils;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {

	private static XStream xStream = new XStream();
	
	public static <T> String java2xml(T t ){
		String xmlString = null;
	
		xmlString = xStream.toXML(t);
		
		return xmlString;
	}
	
	public static <T> String java2xml(T t ,Class<T> valueType){
		String xmlString = null;
		xStream.alias(valueType.getName(), valueType);
		xmlString = xStream.toXML(t);
		
		return xmlString;
	}
	
	public static <T> String java2xml(T t, String str){
		String xmlString = null;
		xStream.alias(str,t.getClass());
		xmlString = xStream.toXML(t);
		
		return xmlString;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T xml2java(String xml ,Class<T> valueType){
		T obj = null;
		obj = (T) xStream.fromXML(xml, valueType);
		return obj;
	}
}
