package okon.ASE9;

import okon.ASE9.exception.AppException;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadServicePooled extends LoadService {
    private GatewaySybase db;

    public LoadServicePooled(GatewaySybase db) {
        this.db = db;
    }

    @Override
    public List<Message> calculateDatabaseLoad(int seconds) {
        List<Message> result = null;
        try {
            SQLWarning systemRaport = db.findLoadFor(15);
            String readableSystemRaport = transform(systemRaport);
            String serverVersion = findServerVersion(readableSystemRaport);
            String serverName = findServerName(readableSystemRaport);
            if (serverVersion.contains("Cluster")) {
                String kernelUtilizationSection = substringKernelUtilizationSection(readableSystemRaport);
                result = checkAllPools(kernelUtilizationSection);
                for(Message message : result) {
                    message.setServerName(serverName);
                }
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return result;
    }

    private String transform(SQLWarning systemRaport) {
        StringBuilder result = new StringBuilder();
        do {
            result.append(systemRaport.getMessage().trim()).append("\n");
            systemRaport = systemRaport.getNextWarning();
        } while (systemRaport != null);
        return result.toString();
    }

    private String findServerVersion(String readableSystemRaport) {
        Pattern pattern = Pattern.compile("Server Version:\\s+(.*)\n");
        Matcher matcher = pattern.matcher(readableSystemRaport);
        matcher.find();
        return matcher.group(1);
    }

    private String findServerName(String readableSystemRaport) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(readableSystemRaport);
        matcher.find();
        return matcher.group(1);
    }

    private String substringKernelUtilizationSection(String readableSystemRaport) {
        return readableSystemRaport.substring(readableSystemRaport.indexOf("Engine Utilization (Tick %)"),
                readableSystemRaport.indexOf("-------------------------  ------------  ------------  ----------  ----------\n" + "Server Summary"));
    }

    private List<Message> checkAllPools(String kernelUtilizationSection) {
        String[] threadPoolSections = kernelUtilizationSection.split("\n\n");
        List<Message> kernelPerformanceInformations = new ArrayList();
        for (int i = 0; i < threadPoolSections.length; i++) {
            kernelPerformanceInformations.add(checkPoolProcessorsUsage(threadPoolSections[i]));
        }
        return kernelPerformanceInformations;
    }

    private Message checkPoolProcessorsUsage(String threadPoolSection) {
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
}