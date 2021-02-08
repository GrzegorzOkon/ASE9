package okon.ASE9;

import okon.ASE9.exception.AppException;
import okon.ASE9.exception.ConnectionException;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {
    public static List<Report> getMessages(Job job) {
        List<Report> result = new ArrayList<>();
        try (GatewaySybase db = GatewayFactory.make(job)) {
            PerformanceService service = PerformanceServiceFactory.make(job, db);
            result = service.reportProcessorPerformance(job.getTime(), job.getServer());
        } catch (ConnectionException e) {
            Report report = new ExceptionReport();
            report.setAlias(job.getServer().getAlias());
            report.setServerIP(job.getServer().getIp());
            result.add(report);
        } catch (Exception e) {
            throw new AppException(e);
        }
        return result;
    }
}