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
            validate(result);
        } catch (Exception e) {
            throw new ConfigurationException(e.getMessage());
        }
        return result;
    }

    static void validate(Properties properties) {
        validateProcedureExecutionTime(properties);
        validateReportFormat(properties);
    }

    static void validateProcedureExecutionTime(Properties properties) {
        if (isKeyAbsent(properties, "ProcedureExecutionTime") || isWrongFormat(properties, "ProcedureExecutionTime") ||
                isOutOfRange(properties, "ProcedureExecutionTime")) {
            setDefaultProcedureExecutionTime(properties);
        } else {
            setRightProcedureExecutionTimeFormat(properties);
        }
    }

    static void validateReportFormat(Properties properties) {
        if (isKeyAbsent(properties, "ReportFormat") || isWrongValue(properties, "ReportFormat")) {
            setDefaultReportFormat(properties);
        }
    }

    static boolean isKeyAbsent(Properties properties, String key) {
        if (properties.containsKey(key)) return false;
        return true;
    }

    static boolean isWrongFormat(Properties properties, String key) {
        try {
            Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
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

    static void setDefaultProcedureExecutionTime(Properties properties) {
        properties.setProperty("ProcedureExecutionTime", "00:00:10");
    }

    static void setDefaultReportFormat(Properties properties) {
        properties.setProperty("ReportFormat", "os");
    }

    static void setRightProcedureExecutionTimeFormat(Properties properties) {
        int hours = Integer.valueOf(Integer.valueOf(properties.getProperty("ProcedureExecutionTime")) / 3600);
        int minutes = (Integer.valueOf(properties.getProperty("ProcedureExecutionTime")) - hours) / 60;
        int seconds = Integer.valueOf(properties.getProperty("ProcedureExecutionTime")) % 60;
        properties.setProperty("ProcedureExecutionTime", String.format("%02d", hours) + ":"
                + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }
}
