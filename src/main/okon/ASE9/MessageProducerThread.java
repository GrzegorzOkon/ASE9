package okon.ASE9;

import javax.sql.DataSource;

import static okon.ASE9.ASE9App.connectionFactory;
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
                Message message = displayKernelPerformanceInformation(job);

                synchronized (messageList) {
                    messageList.add(message);
                }
            }
        }
    }

    public Message displayKernelPerformanceInformation(DataSource dataSource) {
        Message message = null;

        try (SybConnection connection = connectionFactory.build(dataSource)) {
            message = connection.execute();
        } catch (Exception e) {
            throw new AppException(e);
        }

        return message;
    }
}
