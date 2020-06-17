package okon.ASE9;

import okon.ASE9.config.AuthorizationConfigReader;
import okon.ASE9.config.CommandConfigReader;
import okon.ASE9.config.ServerConfigReader;
import okon.ASE9.exception.AppException;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ASE9App {
    static final Queue<Job> jobs = new LinkedList<>();
    static final List<Report> messages = new ArrayList();

    public static void main(String[] args) {
        initializeQueue();
        startThreadPool(jobs.size());
        print();
    }

    static void initializeQueue() {
        List<Server> servers = ServerConfigReader.readParams((new File("./config/servers.xml")));
        List<Authorization> authorizations = AuthorizationConfigReader.readParams((new File("./config/server-auth.xml")));
        String time = CommandConfigReader.readParameter(new File("./config/command.xml"));
        createJobs(servers, authorizations, time);
    }

    static void createJobs(List<Server> servers, List<Authorization> authorizations, String time) {
        for (Server server : servers) {
            Job job = new Job(server, matchAuthorizationToServer(server, authorizations), time);
            jobs.add(job);
        }
    }

    static Authorization matchAuthorizationToServer(Server server, List<Authorization> authorizations) {
        if (isAuthorizationPresent(server)) {
            for (Authorization authorization : authorizations) {
                if (server.getAuthorizationInterface().equals(authorization.getAuthorizationInterface())) {
                    return authorization;
                }
            }
        }
        return null;
    }

    static boolean isAuthorizationPresent(Server server) {
        if (!server.getAuthorizationInterface().equals("")) {
            return true;
        }
        return false;
    }

    static void startThreadPool(int threadSum) {
        Thread[] threads = new Thread[threadSum];
        for (int i = 0; i < threadSum; i++) {
            threads[i] = new ReportProducerThread();
        }
        for (int i = 0; i < threadSum; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadSum; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
    }

    static void print() {
        printToConsole();
        printToFile();
    }

    static void printToConsole() {
        ReportFormatter formatter = new ReportFormatter(messages);
        System.out.println(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
        System.out.println(formatter.format(new String[]{"------", "----------", "--------"}));
        for (Report report : messages) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        report.getThreadPool(), formatter.getCPUBusy(report.getIdle()) + " %"});
                System.out.println(formattedRow);
            } else if (report instanceof ExceptionReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        "connection error", ""});
                System.out.println(formattedRow);
            }
        }
    }

    static void printToFile() {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            ReportFormatter formatter = new ReportFormatter(messages);
            out.write(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"------", "----------", "--------"}));
            out.write(System.getProperty("line.separator"));
            for (Report report : messages) {
                if (report instanceof PerformanceReport) {
                    String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                            report.getThreadPool(), formatter.getCPUBusy(report.getIdle()) + " %"});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                } else if (report instanceof ExceptionReport) {
                    String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                            "connection error", ""});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    static String getJarFileName() {
        String path = ASE9App.class.getResource(ASE9App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }
}
