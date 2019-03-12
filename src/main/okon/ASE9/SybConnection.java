package okon.ASE9;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SybConnection implements Closeable {
    private final Connection connection;

    public SybConnection(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }

    public List<Message> execute() {
        SQLWarning warnings = executeStoredProcedure();
        String response = changeToText(warnings);

        String serverName = null;
        String kernelUtilizationSection = null;
        List<Message> kernelPerformanceInformations;

        serverName = checkServerName(response);

        kernelUtilizationSection = substringKernelUtilizationSection(response);
        kernelPerformanceInformations = checkAllPools(kernelUtilizationSection);

        for(Message message : kernelPerformanceInformations) {
            message.setServerName(serverName);
        }

        return kernelPerformanceInformations;
    }

    public SQLWarning executeStoredProcedure() {
        String sql = "sp_sysmon '00:00:15', kernel";
        SQLWarning response = null;

        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate(sql);
            response = stmt.getWarnings();
        } catch (SQLException e) {
            throw new AppException(e);
        }

        return response;
    }

    public String changeToText(SQLWarning response) {
        StringBuilder warnings = new StringBuilder();

        do {
            warnings.append(response.getMessage().trim()).append("\n");
            response = response.getNextWarning();
        } while (response != null);

        return warnings.toString();
    }

    public String checkServerName(String response) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(response);
        matcher.find();

        return matcher.group(1);
    }

    public String substringKernelUtilizationSection(String response) {
        return response.substring(response.indexOf("Engine Utilization (Tick %)"), response.indexOf("-------------------------  ------------  ------------  ----------  ----------\n" +
                "Server Summary"));
    }

    public List<Message> checkAllPools(String kernelUtilizationSection) {
        String[] threadPoolSections = splitForThreadPoolSections(kernelUtilizationSection);
        List<Message> kernelPerformanceInformations = new ArrayList();

        for (int i = 0; i < threadPoolSections.length; i++) {
            kernelPerformanceInformations.add(checkPoolProcessorsUsage(threadPoolSections[i]));
        }

        return kernelPerformanceInformations;
    }

    public String[] splitForThreadPoolSections(String kernelUtilizationSection) {
        return kernelUtilizationSection.split("\n\n");
    }

    public Message checkPoolProcessorsUsage(String threadPoolSection) {
        Message kernelPerformanceInformation = new Message();

        Pattern pattern = Pattern.compile("ThreadPool :\\s(\\w+)\n");
        Matcher matcher = pattern.matcher(threadPoolSection);
        matcher.find();

        kernelPerformanceInformation.setThreadPool(matcher.group(1));

        pattern = Pattern.compile("Average\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%");
        matcher = pattern.matcher(threadPoolSection);
        matcher.find();

        kernelPerformanceInformation.setUserBusy(matcher.group(1));
        kernelPerformanceInformation.setSystemBusy(matcher.group(2));
        kernelPerformanceInformation.setIoBusy(matcher.group(3));
        kernelPerformanceInformation.setIdle(matcher.group(4));

        return kernelPerformanceInformation;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new AppException(e);
        }
    }
}