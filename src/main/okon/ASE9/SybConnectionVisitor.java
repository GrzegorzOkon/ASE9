package okon.ASE9;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SybConnectionVisitor {
    private String kernelUtilizationSection;
    List<Message> kernelPerformanceInformations;

    public List<Message> searchPerformanceReport(SybConnection sybConnection) {
        String serverVersion = findServerVersion(sybConnection);
        String serverName = findServerName(sybConnection);

        if (serverVersion.contains("Cluster")) {
            kernelUtilizationSection = substringKernelUtilizationSection(sybConnection);
            kernelPerformanceInformations = checkAllPools(kernelUtilizationSection);

            for(Message message : kernelPerformanceInformations) {
                message.setServerName(serverName);
            }
        }

        return kernelPerformanceInformations;
    }

    String findServerVersion(SybConnection sybConnection) {
        Pattern pattern = Pattern.compile("Server Version:\\s+(.*)\n");
        Matcher matcher = pattern.matcher(sybConnection.response);
        matcher.find();

        return matcher.group(1);
    }

    String findServerName(SybConnection sybConnection) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(sybConnection.response);
        matcher.find();

        return matcher.group(1);
    }

    String substringKernelUtilizationSection(SybConnection sybConnection) {
        return sybConnection.response.substring(sybConnection.response.indexOf("Engine Utilization (Tick %)"),
                sybConnection.response.indexOf("-------------------------  ------------  ------------  ----------  ----------\n" +
                "Server Summary"));
    }

    List<Message> checkAllPools(String kernelUtilizationSection) {
        String[] threadPoolSections = splitForThreadPoolSections(kernelUtilizationSection);
        List<Message> kernelPerformanceInformations = new ArrayList();

        for (int i = 0; i < threadPoolSections.length; i++) {
            kernelPerformanceInformations.add(checkPoolProcessorsUsage(threadPoolSections[i]));
        }

        return kernelPerformanceInformations;
    }

    String[] splitForThreadPoolSections(String kernelUtilizationSection) {
        return kernelUtilizationSection.split("\n\n");
    }

    Message checkPoolProcessorsUsage(String threadPoolSection) {
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
