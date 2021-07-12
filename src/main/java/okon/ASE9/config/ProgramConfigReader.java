package okon.ASE9.config;
import okon.ASE9.exception.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void validate(Properties properties) {
        validateLogFile(properties);
        validateLogFileSize(properties);
        validateDebugLevel(properties);
        validateReportFile(properties);
        validateReportType(properties);
        validateServer(properties);
        validateMaskType(properties);
        validateProcedureExecutionTime(properties);
        validateThreadSum(properties);
    }

    public static void validateLogFile(Properties properties) {
        if (properties.containsKey("LogFile") && isWrongFormat(properties, "LogFile")) {
            System.exit(101);
        }
    }

    public static void validateLogFileSize(Properties properties) {
        if (properties.containsKey("LogFileSize") && (isWrongFormat(properties, "LogFileSize")
                || isOutOfRange(properties, "LogFileSize"))) {
            System.exit(102);
        }
    }

    public static void validateDebugLevel(Properties properties) {
        if (properties.containsKey("DebugLevel") && (isWrongFormat(properties, "DebugLevel")
                || isOutOfRange(properties, "DebugLevel"))) {
            System.exit(103);
        }
    }

    public static void validateReportFile(Properties properties) {
        if (properties.containsKey("ReportFile") && isWrongFormat(properties, "ReportFile")) {
            System.exit(104);
        }
    }

    static void validateReportType(Properties properties) {
        if (properties.containsKey("ReportType") && isWrongValue(properties, "ReportType")) {
            System.exit(105);
        }
    }

    static void validateServer(Properties properties) {
        if (properties.containsKey("Server")) {
            String validatedServers = "";
            for (String server : properties.getProperty("Server").split(";")) {
                if (isIPAbsent(server) || isPortAbsent(server) || isLoginAbsent(server) || isPasswordAbsent(server)
                        || isAliasAbsent(server) || isIPWrongFormat(server) || isPortWrongFormat(server)
                        || isLoginWrongFormat(server)) {
                    System.exit(106);
                } else {
                    validatedServers = validatedServers + server + ";";
                }
            }
            properties.setProperty("Server", validatedServers);
        }
    }

    static void validateMaskType(Properties properties) {
        if (properties.containsKey("MaskType") && isWrongValue(properties, "MaskType")) {
            System.exit(107);
        }
    }

    static void validateProcedureExecutionTime(Properties properties) {
        if (properties.containsKey("ProcedureExecutionTime") && (isWrongFormat(properties, "ProcedureExecutionTime") ||
                isOutOfRange(properties, "ProcedureExecutionTime"))) {
            System.exit(108);
        }
    }

    static void validateThreadSum(Properties properties) {
        if (properties.containsKey("ThreadSum") && (isWrongFormat(properties, "ThreadSum") ||
                isOutOfRange(properties, "ThreadSum"))) {
            System.exit(109);
        }
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

    public static boolean isAliasAbsent(String server) {
        if (server.contains("{") && server.contains("}") && server.substring(server.indexOf("{") + 1, server.indexOf("}")).length() > 0) return false;
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

    public static boolean isWrongFormat(Properties properties, String key) {
        if (key.equals("LogFile") || key.equals("ReportFile")) {
            try {
                new File(properties.getProperty(key)).getCanonicalPath();
            } catch (IOException e) {
                return true;
            }
        } else if (key.equals("LogFileSize") || key.equals("DebugLevel") || key.equals("ProcedureExecutionTime") ||
                key.equals("ThreadSum")) {
            try {
                Integer.parseInt(properties.getProperty(key));
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOutOfRange(Properties properties, String key) {
        if (key.equals("LogFileSize")) {
            if (Integer.valueOf(properties.getProperty("LogFileSize")).intValue() < 1
                    || Integer.valueOf(properties.getProperty("LogFileSize")).intValue() > 128) {
                return true;
            }
        } else if (key.equals("DebugLevel")) {
            if (Integer.valueOf(properties.getProperty("DebugLevel")).intValue() < 0
                    || Integer.valueOf(properties.getProperty("DebugLevel")).intValue() > 5) {
                return true;
            }
        } else if (key.equals("ProcedureExecutionTime")) {
            if (Integer.valueOf(properties.getProperty("ProcedureExecutionTime")).intValue() < 0
                    || Integer.valueOf(properties.getProperty("ProcedureExecutionTime")).intValue() > 86400) {
                return true;
            }
        } else if (key.equals("ThreadSum")) {
            if (Integer.valueOf(properties.getProperty("ThreadSum")).intValue() < 1
                || Integer.valueOf(properties.getProperty("ThreadSum")).intValue() > 10) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWrongValue(Properties properties, String key) {
        if (key.equals("ReportType")) {
            if (properties.getProperty(key).toLowerCase().equals("tick") || properties.getProperty(key).toLowerCase().equals("os")
                    || properties.getProperty(key).toLowerCase().equals("complete"))
                return false;
        } else if (key.equals("MaskType")) {
            if (properties.getProperty(key).toLowerCase().equals("unmasked")
                    || properties.getProperty(key).toLowerCase().equals("hex")) {
                return false;
            }
        }
        return true;
    }
}
