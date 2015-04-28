package com.idove.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * @author : kardel
 * @date : 2015-1-8
 * 
 */
public class PropertiesUtil {

	public static String getPropertiesValue(String propertiesName,String propertiesKey){
		String value = "";
		Properties props;
		try {
			props = PropertiesLoaderUtils
					.loadAllProperties("config/" + propertiesName);
			value = props.getProperty(propertiesKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
