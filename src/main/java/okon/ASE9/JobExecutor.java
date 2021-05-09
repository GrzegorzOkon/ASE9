package okon.ASE9;

import okon.ASE9.db.Gateway;
import okon.ASE9.db.GatewayFactory;
import okon.ASE9.exception.AppException;
import okon.ASE9.exception.ConnectionException;
import okon.ASE9.messages.ExceptionDataExtraction;
import okon.ASE9.messages.DataExtraction;
import okon.ASE9.service.PerformanceService;
import okon.ASE9.service.PerformanceServiceFactory;

import java.util.ArrayList;
import java.util.List;

public class JobExecutor {
    public static List<DataExtraction> getMessages(Job job) {
        List<DataExtraction> result = new ArrayList<>();
        try (Gateway db = GatewayFactory.make(job)) {
            PerformanceService service = PerformanceServiceFactory.make(db);
            result = service.checkServerPerformance(job.getTime(), job.getServer());
        } catch (ConnectionException e) {
            DataExtraction report = new ExceptionDataExtraction();
            report.setAlias(job.getServer().getAlias());
            report.setServerIP(job.getServer().getIp());
            result.add(report);
        } catch (Exception e) {
            throw new AppException(e);
        }
        return result;
    }
}