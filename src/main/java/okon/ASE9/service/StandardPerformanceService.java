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
            SQLWarning systemRaport = db.findLoadFor(time);
            String readableSystemRaport = transformToPlainText(systemRaport);
            Extraction raport = extractEngineUsage(readableSystemRaport);
            raport.setAlias(server.getAlias());
            raport.setServerIP(server.getIp());
            raport.setServerName(extractServerName(readableSystemRaport));
            result.add(raport);
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

    public Extraction extractEngineUsage(String readableSystemRaport) {
        PerformanceExtraction result = new PerformanceExtraction();
        Pattern pattern = Pattern.compile("Average\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%\\s+(\\d+.\\d)\\s%");
        Matcher matcher = pattern.matcher(readableSystemRaport);
        matcher.find();
        result.setCpuBusy(matcher.group(1));
        result.setIoBusy(matcher.group(2));
        result.setIdle(matcher.group(3));
        return result;
    }

    public String extractServerName(String readableSystemRaport) {
        Pattern pattern = Pattern.compile("Server Name:\\s+(\\w+)\n");
        Matcher matcher = pattern.matcher(readableSystemRaport);
        matcher.find();
        return matcher.group(1);
    }
}
