package util;

import java.io.FileReader;
import java.util.Properties;

/**
 * Configuration file reader class
 * 
 * @author bettyrain
 */

public class ConfigFileReader {

	private Properties configFile;

	public ConfigFileReader() {
		this.configFile = new Properties();
		try (FileReader reader = new FileReader("config")) {
			configFile.load(reader);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}
}
