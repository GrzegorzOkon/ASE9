package okon.ASE9;

import javax.sql.DataSource;

import java.util.List;

import static okon.ASE9.ASE9App.dataSourceQueue;
import static okon.ASE9.ASE9App.messageList;

public class MessageProducerThread extends Thread {

    @Override
    public void run() {
        while (!dataSourceQueue.isEmpty()) {
            DataSource job = null;

            synchronized (dataSourceQueue) {
                if (!dataSourceQueue.isEmpty()) {
                    job = dataSourceQueue.poll();
                }
            }

            if (job != null) {
                List<Message> messages = displayKernelPerformanceInformation(job);
                synchronized (messageList) {
                    for (Message message : messages)
                        messageList.add(message);
                }
            }
        }
    }

    /*public List<Message> displayKernelPerformanceInformation(DataSource dataSource) {
        List<Message> message = null;
        try (SybConnection connection = connectionFactory.build(dataSource)) {
            message = connection.execute();
        } catch (Exception e) {
            throw new AppException(e);
        }
        return message;
    }*/

    public List<Message> displayKernelPerformanceInformation(DataSource dataSource) {
        List<Message> message = null;
        try (SybGateway db = GatewayFactory.make(dataSource)) {
            LoadService service = new LoadService(db);
            message = service.calculateDatabaseLoad(15);
        } catch (Exception e) {
            throw new AppException(e);
        }
        return message;
    }
}
