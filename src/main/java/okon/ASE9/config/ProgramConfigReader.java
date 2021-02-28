package okon.ASE9.config;
import okon.ASE9.exception.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ProgramConfigReader {
    public static Properties loadProperties(File file) {
        Properties result = new Properties();
        try {
            result.load(new FileInputStream(file));
        } catch (Exception e) {
            throw new ConfigurationException(e.getMessage());
        }
        return result;
    }
}
