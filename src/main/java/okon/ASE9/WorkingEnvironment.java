package okon.ASE9;

import java.util.Properties;

public class WorkingEnvironment {
    private static Properties environment = new Properties();

    public static void setEnvironment(Properties parameters) {
        if (parameters.containsKey("ProcedureExecutionTime")) {
            environment.setProperty("ProcedureExecutionTime", formatProcedureExecutionTime(parameters.getProperty("ProcedureExecutionTime")));
        }
        environment.setProperty("ApplicationName", getJarFileName());
    }

    private static String formatProcedureExecutionTime(String seconds) {
        int hrs = Integer.valueOf(seconds) / 3600;
        int min = (Integer.valueOf(seconds) - hrs) / 60;
        int sec = Integer.valueOf(seconds) % 60;
        return String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    private static String getJarFileName() {
        String path = App.class.getResource(App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }

    public static String getApplicationName() {
        return environment.getProperty("ApplicationName");
    }

    public static String getLogFileSize() {
        return environment.getProperty("LogFileSize", "1");
    }

    public static String getProcedureExecutionTime() {
        return environment.getProperty("ProcedureExecutionTime", "00:00:10");
    }

    public static String getReportFormat() {
        return environment.getProperty("ReportFormat", "os");
    }
}
