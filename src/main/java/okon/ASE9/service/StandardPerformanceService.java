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

public class StandardPerformanceService extends PerformanceService {
    private GatewayToSybase db;

    public StandardPerformanceService(GatewayToSybase db) {
        this.db = db;
    }

    @Override
    public List<Extraction> reportProcessorPerformance(String time, Server server) {
        List<Extraction> result = new ArrayList<>();
        try {
            SQLWarning rawSystemReport = db.findLoadFor(time);
            String systemReport = transformToPlainText(rawSystemReport);
            Extraction report = matchStatistics(systemReport);
            report.setServerName(matchServerName(systemReport));
            report.setAlias(server.getAlias());
            report.setServerIP(server.getIp());
            result.add(report);
        } catch (SQLException e) {
            throw new AppException(e);
        }
        return result;
    }

    public String transformToPlainText(SQLWarning systemRaport) {
        StringBuilder result = new StringBuilder();
        do {
            result.append(systemRaport.getMessage().trim()).append("\n");
            systemRaport = systemRaport.getNextWarning();
        } while (systemRaport != null);
        return result.toString();
    }

    public Extraction matchStatistics(String systemReport) {
        PerformanceExtraction result = new PerformanceExtraction();
        Pattern pattern = Pattern.compile("Average\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%");
        Matcher matcher = pattern.matcher(systemReport);
        matcher.find();
        //result.setIdleTick(matcher.group(1));
        result.setIoBusyTick(matcher.group(2));
        result.setIdleTick(matcher.group(3));
        return result;
    }

    public String matchServerName(String systemRaport) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(systemRaport);
        matcher.find();
        return matcher.group(1);
    }
}
