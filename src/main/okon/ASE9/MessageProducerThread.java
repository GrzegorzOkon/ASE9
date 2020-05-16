package okon.ASE9;

import java.util.List;

import static okon.ASE9.ASE9App.jobs;
import static okon.ASE9.ASE9App.messages;

public class MessageProducerThread extends Thread {
    @Override
    public void run() {
        while (!jobs.isEmpty()) {
            Job job = null;
            synchronized (jobs) {
                if (!jobs.isEmpty()) {
                    job = jobs.poll();
                }
            }
            if (job != null) {
                List<Message> messageList = MessageManager.getMessages(job);
                synchronized (messages) {
                    for (Message message : messageList)
                        messages.add(message);
                }
            }
        }
    }
}
