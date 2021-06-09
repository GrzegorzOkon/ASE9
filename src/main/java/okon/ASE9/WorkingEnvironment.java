package okon.ASE9;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class WorkingEnvironment {
    private static Properties environment = new Properties();

    public static void setEnvironment(Properties parameters) {
        if (parameters.containsKey("LogFile")) {
            environment.setProperty("LogFile", parameters.getProperty("LogFile"));
        }
        if (parameters.containsKey("LogFileSize")) {
            environment.setProperty("LogFileSize", parameters.getProperty("LogFileSize"));
        }
        if (parameters.containsKey("DebugLevel")) {
            environment.setProperty("DebugLevel", parameters.getProperty("DebugLevel"));
        }
        if (parameters.containsKey("ReportFile")) {
            environment.setProperty("ReportFile", parameters.getProperty("ReportFile"));
        }
        if (parameters.containsKey("ReportType")) {
            environment.setProperty("ReportType", parameters.getProperty("ReportType"));
        }
        if (parameters.containsKey("ProcedureExecutionTime")) {
            environment.setProperty("ProcedureExecutionTime", formatProcedureExecutionTime(parameters.getProperty("ProcedureExecutionTime")));
        }
        environment.setProperty("ApplicationName", checkJarFileName());
        environment.setProperty("HostName", checkHostName());
    }

    private static String formatProcedureExecutionTime(String seconds) {
        int hrs = Integer.valueOf(seconds) / 3600;
        int min = (Integer.valueOf(seconds) - hrs) / 60;
        int sec = Integer.valueOf(seconds) % 60;
        return String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    private static String checkJarFileName() {
        String path = App.class.getResource(App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }

    private static String checkHostName() {
        String result = "Unknown";
        try {
            result = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
        }
        return result;
    }

    public static String getLogFile() {
        return environment.getProperty("LogFile", "./" + getApplicationName() + ".log");
    }

    public static String getLogFileSize() {
        return environment.getProperty("LogFileSize", "1");
    }

    public static Integer getDebugLevel() {
        return Integer.valueOf(environment.getProperty("DebugLevel", "3"));
    }

    public static String getReportFile() {
        return environment.getProperty("ReportFile", "./" + getApplicationName() + ".txt");
    }

    public static String getReportType() {
        return environment.getProperty("ReportType", "os");
    }

    public static String getProcedureExecutionTime() {
        return environment.getProperty("ProcedureExecutionTime", "00:00:10");
    }

    public static String getApplicationName() {
        return environment.getProperty("ApplicationName");
    }

    public static String getHostName() {
        return environment.getProperty("HostName");
    }
}
