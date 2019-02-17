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

    /*private Message checkService(HttpDetailsJob job, int checkingSum) {
        int correctChecksCounter = 0;
        ConnectionFactory connectionFactory = chooseFactory(job);

        for (int i = 0; i < checkingSum; i++) {
            if (isCorrectService(job, connectionFactory)) {
                correctChecksCounter++;
            }
        }

        return new Message(job.getUrl(), job.getDescription(), correctChecksCounter, checkingSum);
    }*/

    /*private ConnectionFactory chooseFactory(HttpDetailsJob job) {
        if (job.getUrl().contains("http://") && job.getLogin() == null) {
            return httpConnectionFactory;
        } else if (job.getUrl().contains("http://") && job.getLogin() != null) {
            return domainHttpConnectionFactory;
        } else if (job.getUrl().contains("https://") && job.getLogin() == null) {
            return httpsConnectionFactory;
        } else {
            return domainHttpsConnectionFactory;
        }
    }*/

    /*private boolean isCorrectService(HttpDetailsJob job, ConnectionFactory connectionFactory) {
        try (Connection connection = connectionFactory.build(job)) {
            try {
                String response = connection.response();
                if (response != null) {
                    return true;
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        }

        return false;
    }*/
}
