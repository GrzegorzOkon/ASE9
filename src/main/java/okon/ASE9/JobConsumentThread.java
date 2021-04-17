package okon.ASE9;

import okon.ASE9.service.Extraction;

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
                List<Extraction> messageList = JobExecutor.getMessages(job);
                synchronized (extractions) {
                    extractions.add(messageList);
                }
            }
        }
    }
}
