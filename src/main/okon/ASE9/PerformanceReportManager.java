package okon.ASE9;

import okon.ASE9.exception.AppException;

import java.util.List;

public class PerformanceReportManager {
    public static List<PerformanceReport> getMessages(Job job) {
        List<PerformanceReport> result = null;
        try (GatewaySybase db = GatewayFactory.make(job)) {
            PerformanceService service = PerformanceServiceFactory.make(job, db);
            result = service.reportProcessorPerformance(job.getTime(), job.getServer().getIp());
        } catch (Exception e) {
            throw new AppException(e);
        }
        return result;
    }
}