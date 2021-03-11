package okon.ASE9.service;

import okon.ASE9.PerformanceExtraction;
import okon.ASE9.Extraction;
import okon.ASE9.Server;
import okon.ASE9.db.GatewayToSybase;
import okon.ASE9.exception.AppException;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PooledPerformanceService extends PerformanceService {
    private GatewayToSybase db;

    public PooledPerformanceService(GatewayToSybase db) {
        this.db = db;
    }

    @Override
    public List<Extraction> reportProcessorPerformance(String time, Server server) {
        List<Extraction> result = null;
        try {
            SQLWarning systemRaport = db.findLoadFor(time);
            String readableSystemRaport = transformToPlainText(systemRaport);
            String serverVersion = matchServerVersion(readableSystemRaport);
            String serverName = matchServerName(readableSystemRaport);
            if (serverVersion.contains("Cluster")) {
                String engineUtilizationSection = substringEngineUtilizationSection(readableSystemRaport);
                String threadUtilizationSection = substringThreadUtilizationSection(readableSystemRaport);
                List<PerformanceExtraction> engines = checkEnginePools(engineUtilizationSection);
                List<PerformanceExtraction> threads = checkThreadPools(threadUtilizationSection);
                result = joinTheSamePools(engines, threads);
                setServerProperties(result, serverName, server);
            }
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return result;
    }

    private String transformToPlainText(SQLWarning systemRaport) {
        StringBuilder result = new StringBuilder();
        do {
            result.append(systemRaport.getMessage().trim()).append("\n");
            systemRaport = systemRaport.getNextWarning();
        } while (systemRaport != null);
        return result.toString();
    }

    private String matchServerVersion(String readableSystemRaport) {
        Pattern pattern = Pattern.compile("Server Version:\\s+(.*)\n");
        Matcher matcher = pattern.matcher(readableSystemRaport);
        matcher.find();
        return matcher.group(1);
    }

    private String matchServerName(String readableSystemRaport) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(readableSystemRaport);
        matcher.find();
        return matcher.group(1);
    }

    private String substringEngineUtilizationSection(String readableSystemRaport) {
        return readableSystemRaport.substring(readableSystemRaport.indexOf("Engine Utilization (Tick %)"),
                readableSystemRaport.indexOf("-------------------------  ------------  ------------  ----------  ----------\n" + "Server Summary"));
    }

    public String substringThreadUtilizationSection(String readableSystemRaport) {
        return readableSystemRaport.substring(readableSystemRaport.indexOf("Thread Utilization (OS %)"),
                readableSystemRaport.indexOf("-------------------------  ------------  ------------  ----------\n" + "Server Summary",
                        readableSystemRaport.indexOf("-------------------------  ------------  ------------  ----------\n" + "Server Summary") + 1));
    }

    private List<PerformanceExtraction> checkEnginePools(String engineSection) {
        List<PerformanceExtraction> result = new ArrayList();
        String[] enginePoolSections = engineSection.split("\n\n");
        for (int i = 0; i < enginePoolSections.length; i++) {
            if (!isEmptyPool(enginePoolSections[i])) {
                result.add(extractEngineUsage(enginePoolSections[i]));
            }
        }
        return result;
    }

    private List<PerformanceExtraction> checkThreadPools(String threadSection) {
        List<PerformanceExtraction> result = new ArrayList<>();
        String[] threadPoolSections = threadSection.split("\n\n");
        for (int i = 0; i < threadPoolSections.length; i++) {
            if (!isEmptyPool(threadPoolSections[i])) {
                result.add(extractThreadUsage(threadPoolSections[i]));
            }
        }
        return result;
    }

    public boolean isEmptyPool(String pool) {
        if (pool.contains("Average"))
            return false;
        return true;
    }

    private PerformanceExtraction extractEngineUsage(String enginePoolSection) {
        PerformanceExtraction result = new PerformanceExtraction();
        Pattern pattern = Pattern.compile("ThreadPool :\\s(\\w+)\n");
        Matcher matcher = pattern.matcher(enginePoolSection);
        matcher.find();
        result.setThreadPool(matcher.group(1));
        pattern = Pattern.compile("Average\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%");
        matcher = pattern.matcher(enginePoolSection);
        matcher.find();
        result.setUserBusyTick(matcher.group(1));
        result.setSystemBusyTick(matcher.group(2));
        result.setIoBusyTick(matcher.group(3));
        result.setIdleTick(matcher.group(4));
        return result;
    }

    public PerformanceExtraction extractThreadUsage(String threadPoolSection) {
        PerformanceExtraction result = new PerformanceExtraction();
        Pattern pattern = Pattern.compile("ThreadPool :\\s(\\w+)\n");
        Matcher matcher = pattern.matcher(threadPoolSection);
        matcher.find();
        result.setThreadPool(matcher.group(1));
        pattern = Pattern.compile("Average\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%");
        matcher = pattern.matcher(threadPoolSection);
        matcher.find();
        result.setUserBusyOS(matcher.group(1));
        result.setSystemBusyOS(matcher.group(2));
        result.setIdleOS(matcher.group(3));
        return result;
    }

    private List<Extraction> joinTheSamePools(List<PerformanceExtraction> engines, List<PerformanceExtraction> threads) {
        List<Extraction> result = new ArrayList<>();
        for (PerformanceExtraction engine : engines) {
            for (PerformanceExtraction thread : threads) {
                if (engine.getThreadPool().equals(thread.getThreadPool())) {
                    PerformanceExtraction report = new PerformanceExtraction();
                    report.setThreadPool(engine.getThreadPool());
                    report.setUserBusyTick(engine.getUserBusyTick());
                    report.setSystemBusyTick(engine.getSystemBusyTick());
                    report.setIoBusyTick(engine.getIoBusyTick());
                    report.setIdleTick(engine.getIdleTick());
                    report.setUserBusyOS(thread.getUserBusyOS());
                    report.setSystemBusyOS(thread.getSystemBusyOS());
                    report.setIdleOS(thread.getIdleOS());
                    result.add(report);
                }
            }
        }
        return result;
    }

    private void setServerProperties(List<Extraction> reports, String serverName, Server server) {
        for(Extraction message : reports) {
            message.setServerName(serverName);
            message.setAlias(server.getAlias());
            message.setServerIP(server.getIp());
        }
    }
}