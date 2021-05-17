package okon.ASE9.config;
import okon.ASE9.exception.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramConfigReader {
    private static final Logger logger = LogManager.getLogger(ProgramConfigReader.class);

    public static Properties loadProperties(File file) {
        Properties result = new Properties();
        try {
            result.load(new FileInputStream(file));
            validate(result);
            logger.info("using configuration file: " + file);
        } catch (Exception e) {
            throw new ConfigurationException(e.getMessage());
        }
        return result;
    }

    static void validate(Properties properties) {
        validateReportFormat(properties);
        validateServer(properties);
        validateProcedureExecutionTime(properties);
    }

    static void validateReportFormat(Properties properties) {
        if (properties.containsKey("ReportFormat") && isWrongValue(properties, "ReportFormat")) {
            properties.remove("ReportFormat");
        }
    }

    static void validateServer(Properties properties) {
        if (properties.containsKey("Server")) {
            String validatedServers = "";
            for (String server : properties.getProperty("Server").split(";")) {
                if (isIPAbsent(server) || isPortAbsent(server) || isLoginAbsent(server) || isPasswordAbsent(server) ||
                        isIPWrongFormat(server) || isPortWrongFormat(server) || isLoginWrongFormat(server)) {
                    break;
                } else {
                    validatedServers = validatedServers + server + ";";
                }
            }
            properties.setProperty("Server", validatedServers);
        }
    }

    static void validateProcedureExecutionTime(Properties properties) {
        if (properties.containsKey("ProcedureExecutionTime") && (isWrongFormat(properties, "ProcedureExecutionTime") ||
                isOutOfRange(properties, "ProcedureExecutionTime"))) {
            properties.remove("ProcedureExecutionTime");
        }
    }

    static boolean isWrongFormat(Properties properties, String key) {
        try {
            Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public static boolean isIPAbsent(String server) {
        if (server.contains(":") && server.substring(0, server.indexOf(':')).length() > 0) return false;
        return true;
    }

    public static boolean isPortAbsent(String server) {
        if (server.contains(":") && server.contains("[") && server.substring(server.indexOf(":") + 1, server.indexOf("[")).length() > 0) return false;
        return true;
    }

    public static boolean isLoginAbsent(String server) {
        if (server.contains("[") && server.contains(",") && server.substring(server.indexOf("[") + 1, server.indexOf(",")).length() > 0) return false;
        return true;
    }

    public static boolean isPasswordAbsent(String server) {
        if (server.contains(",") && server.contains("]") && server.substring(server.indexOf(",") + 1, server.indexOf("]")).length() > 0) return false;
        return true;
    }

    public static boolean isIPWrongFormat(String server) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(server.substring(0, server.indexOf(':')));
        if (matcher.find()) return false;
        return true;
    }

    public static boolean isPortWrongFormat(String server) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(server.substring(server.indexOf(":") + 1, server.indexOf("[")));
        if (matcher.find()) return false;
        return true;
    }

    public static boolean isLoginWrongFormat(String server) {
        Pattern pattern = Pattern.compile("^\\D\\w+$");
        Matcher matcher = pattern.matcher(server.substring(server.indexOf("[") + 1, server.indexOf(",")));
        if (matcher.find()) return false;
        return true;
    }

    static boolean isOutOfRange(Properties properties, String key) {
        if (Integer.valueOf(properties.getProperty("ProcedureExecutionTime")).intValue() < 1
                    || Integer.valueOf(properties.getProperty("ProcedureExecutionTime")).intValue() > 86400) return true;
        return false;
    }

    static boolean isWrongValue(Properties properties, String key) {
        if (properties.getProperty(key).toLowerCase().equals("tick") || properties.getProperty(key).toLowerCase().equals("os") ||
                properties.getProperty(key).toLowerCase().equals("complete")) return false;
        return true;
    }
}
