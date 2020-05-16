package okon.ASE9;

import okon.ASE9.exception.AppException;

import java.util.List;

public class MessageManager {
    public static List<Message> getMessages(Job job) {
        List<Message> message = null;
        try (GatewaySybase db = GatewayFactory.make(job)) {
            LoadService service = LoadServiceFactory.make(job, db);
            message = service.calculateDatabaseLoad(15);
        } catch (Exception e) {
            throw new AppException(e);
        }
        return message;
    }
}