package okon.ASE9;

import java.util.List;

import static okon.ASE9.ASE9App.jobs;
import static okon.ASE9.ASE9App.extractions;

public class JobConsumentThread extends Thread {
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
                List<Report> messageList = JobExecutor.getMessages(job);
                synchronized (extractions) {
                    for (Report message : messageList)
                        extractions.add(message);
                }
            }
        }
    }
}
