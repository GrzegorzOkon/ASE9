package okon.ASE9;

import java.util.List;
import okon.ASE9.messages.DataExtraction;
import static okon.ASE9.WorkingObjects.extractions;

public class JobConsumentThread extends Thread {
    @Override
    public void run() {
        while (!WorkingObjects.jobs.isEmpty()) {
            Job job = null;
            synchronized (WorkingObjects.jobs) {
                if (!WorkingObjects.jobs.isEmpty()) {
                    job = WorkingObjects.jobs.poll();
                }
            }
            if (job != null) {
                List<DataExtraction> messageList = JobExecutor.getMessages(job);
                synchronized (extractions) {
                    extractions.add(messageList);
                }
            }
        }
    }
}
