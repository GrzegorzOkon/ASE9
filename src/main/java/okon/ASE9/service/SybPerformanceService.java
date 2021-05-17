package okon.ASE9.service;

import okon.ASE9.App;
import okon.ASE9.WorkingEnvironment;
import okon.ASE9.db.Gateway;
import okon.ASE9.exception.AppException;
import okon.ASE9.messages.DataExtraction;
import okon.ASE9.messages.PerformanceExtractionPooled;
import okon.ASE9.messages.PerformanceExtractionStandard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SybPerformanceService extends PerformanceService {
    private static final Logger logger = LogManager.getLogger(SybPerformanceService.class);
    private Gateway db;

    public SybPerformanceService(Gateway db) {
        this.db = db;
    }

    @Override
    public List<DataExtraction> collectPerfstat(String alias, String ip) {
        logger.debug("In collectPerfstat()");
        List<DataExtraction> result = new ArrayList<>();
        try {
            SQLWarning rawProcedureResult = db.findLoadFor(WorkingEnvironment.getProcedureExecutionTime());
            String transformatedProcedureResult = transformToText(rawProcedureResult);
            String serverName = extractServerName(transformatedProcedureResult);
            if (isPooledVersion(transformatedProcedureResult)) {
                String engineUtilizationSection = substringEngineUtilizationSection(transformatedProcedureResult);
                String threadUtilizationSection = substringThreadUtilizationSection(transformatedProcedureResult);
                List<PerformanceExtractionPooled> engines = checkEnginePools(engineUtilizationSection);
                List<PerformanceExtractionPooled> threads = checkThreadPools(threadUtilizationSection);
                result = joinTheSamePools(engines, threads);
                setServerProperties(result, serverName, alias, ip);
            } else {
                DataExtraction raport = extractStandardEngineUsage(transformatedProcedureResult);
                raport.setAlias(alias);
                raport.setServerIP(ip);
                raport.setServerName(serverName);
                result.add(raport);
            }
        } catch (SQLException e) {
            logger.error("Collecting performance statistics failed on host [[" + ip + "]]");
            throw new AppException(e);
        }
        logger.debug("End of collectPerfstat()");
        return result;
    }

    public String transformToText(SQLWarning systemRaport) {
        StringBuilder result = new StringBuilder();
        do {
            result.append(systemRaport.getMessage().trim()).append("\n");
            systemRaport = systemRaport.getNextWarning();
        } while (systemRaport != null);
        return result.toString();
    }

    private String extractServerName(String procedureResult) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(procedureResult);
        matcher.find();
        return matcher.group(1);
    }

    private boolean isPooledVersion(String procedureResult) {
        if (procedureResult.contains("Pool")) {
            return true;
        }
        return false;
    }

    private String substringEngineUtilizationSection(String procedureResult) {
        return procedureResult.substring(procedureResult.indexOf("Engine Utilization (Tick %)"),
                procedureResult.indexOf("-------------------------  ------------  ------------  ----------  ----------\n" + "Server Summary"));
    }

    public String substringThreadUtilizationSection(String procedureResult) {
        return procedureResult.substring(procedureResult.indexOf("Thread Utilization (OS %)"),
                procedureResult.indexOf("-------------------------  ------------  ------------  ----------\n" + "Server Summary",
                        procedureResult.indexOf("-------------------------  ------------  ------------  ----------\n" + "Server Summary") + 1));
    }

    private List<PerformanceExtractionPooled> checkEnginePools(String engineSection) {
        List<PerformanceExtractionPooled> result = new ArrayList();
        String[] enginePoolSections = engineSection.split("\n\n");
        for (int i = 0; i < enginePoolSections.length; i++) {
            if (!isEmptyPool(enginePoolSections[i])) {
                result.add(extractPooledEngineUsage(enginePoolSections[i]));
            }
        }
        return result;
    }

    private List<PerformanceExtractionPooled> checkThreadPools(String threadSection) {
        List<PerformanceExtractionPooled> result = new ArrayList<>();
        String[] threadPoolSections = threadSection.split("\n\n");
        for (int i = 0; i < threadPoolSections.length; i++) {
            if (!isEmptyPool(threadPoolSections[i])) {
                result.add(extractPooledThreadUsage(threadPoolSections[i]));
            }
        }
        return result;
    }

    public boolean isEmptyPool(String pool) {
        if (pool.contains("Average"))
            return false;
        return true;
    }

    private PerformanceExtractionPooled extractPooledEngineUsage(String enginePoolSection) {
        PerformanceExtractionPooled result = new PerformanceExtractionPooled();
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

    public PerformanceExtractionPooled extractPooledThreadUsage(String threadPoolSection) {
        PerformanceExtractionPooled result = new PerformanceExtractionPooled();
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

    public DataExtraction extractStandardEngineUsage(String procedureResult) {
        PerformanceExtractionStandard result = new PerformanceExtractionStandard();
        Pattern pattern = Pattern.compile("Average\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%");
        Matcher matcher = pattern.matcher(procedureResult);
        matcher.find();
        result.setCpuBusy(matcher.group(1));
        result.setIoBusy(matcher.group(2));
        result.setIdle(matcher.group(3));
        return result;
    }

    private List<DataExtraction> joinTheSamePools(List<PerformanceExtractionPooled> engines, List<PerformanceExtractionPooled> threads) {
        List<DataExtraction> result = new ArrayList<>();
        for (PerformanceExtractionPooled engine : engines) {
            for (PerformanceExtractionPooled thread : threads) {
                if (engine.getThreadPool().equals(thread.getThreadPool())) {
                    PerformanceExtractionPooled report = new PerformanceExtractionPooled();
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

    private void setServerProperties(List<DataExtraction> reports, String serverName, String alias, String ip) {
        for(DataExtraction message : reports) {
            message.setServerName(serverName);
            message.setAlias(alias);
            message.setServerIP(ip);
        }
    }
}
