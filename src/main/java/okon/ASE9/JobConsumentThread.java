package okon.ASE9;

import okon.ASE9.messages.DataExtraction;

import java.util.List;

import static okon.ASE9.App.extractions;

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
