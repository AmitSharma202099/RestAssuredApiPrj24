package com.qa.api.manager;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties prop = new Properties();
	
	static {
		
		try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties"))
		
		{
			if (input != null) 
				{
					prop.load(input);
				}	
		} catch (Exception e)
				{
					e.printStackTrace();
				}
	}
	
	public static String get(String key) {	
		return prop.getProperty(key);
	}

	
}
