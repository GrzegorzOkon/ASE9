package okon.ASE9;

import okon.ASE9.config.*;
import okon.ASE9.exception.AppException;
import okon.ASE9.report.ReportManager;
import okon.ASE9.service.DataExtraction;

import java.io.File;
import java.util.*;

public class ASE9App {
    public static final Properties parameters;
    public static final Queue<Job> jobs = new LinkedList<>();
    public static final List<List<DataExtraction>> extractions = new ArrayList();

    static final String CONNECTION_EXCEPTION_COMMUNICATE = "connection error to";

    static {
        parameters = ProgramConfigReader.loadProperties((new File("./config/program.properties")));
        initializeQueue();
    }

    static void initializeQueue() {
        List<Server> servers = ServerConfigReader.readParams((new File("./config/servers.xml")));
        List<Authorization> authorizations = AuthorizationConfigReader.readParams((new File("./config/server-auth.xml")));
        String time = ProcedureConfigReader.readParameter(new File("./config/procedure.xml"));
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

    public static void main(String[] args) {
        startThreadPool();
        print();
    }

    static void startThreadPool() {
        int threadSum = jobs.size();
        Thread[] threads = new Thread[threadSum];
        for (int i = 0; i < threadSum; i++) {
            threads[i] = new JobConsumentThread();
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

    /*static void print() {
        if (isThreadPoolPresent()) {
            printWithThreadPoolColumnToConsole();
            printWithThreadPoolColumnToFile();
        } else {
            printWithoutThreadPoolColumnToConsole();
            printWithoutThreadPoolColumnToFile();
        }
    }*/

    /*static void printWithThreadPoolColumnToConsole() {
        ReportFormatter formatter = new ReportFormatter(true);
        System.out.println(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
        System.out.println(formatter.format(new String[]{"------", "-----------", "--------"}));
        for (Report report : extractions) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        report.getThreadPool(), formatter.getCPUBusy(report.getIdleTick()) + " %"});
                System.out.println(formattedRow);
            } else if (report instanceof ExceptionReport) {
                String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                        " (" + report.getServerIP() + ")", "", ""});
                System.out.println(formattedRow);
            }
        }
    }*/

    /*static void printWithThreadPoolColumnToFile() {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            ReportFormatter formatter = new ReportFormatter(true);
            out.write(formatter.format(new String[]{"Server", "Thread Pool", "CPU Busy"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"------", "-----------", "--------"}));
            out.write(System.getProperty("line.separator"));
            for (Report report : extractions) {
                if (report instanceof PerformanceReport) {
                    String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                            report.getThreadPool(), formatter.getCPUBusy(report.getIdleTick()) + " %"});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                } else if (report instanceof ExceptionReport) {
                    String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                            " (" + report.getServerIP() + ")", "", ""});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }*/

    /*static void printWithoutThreadPoolColumnToConsole() {
        ReportFormatter formatter = new ReportFormatter(false);
        System.out.println(formatter.format(new String[]{"Server", "CPU Busy"}));
        System.out.println(formatter.format(new String[]{"------", "--------"}));
        for (Report report : extractions) {
            if (report instanceof PerformanceReport) {
                String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                        formatter.getCPUBusy(report.getIdleTick()) + " %"});
                System.out.println(formattedRow);
            } else if (report instanceof ExceptionReport) {
                String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                        " (" + report.getServerIP() + ")", ""});
                System.out.println(formattedRow);
            }
        }
    }*/

    /*static void printWithoutThreadPoolColumnToFile() {
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            ReportFormatter formatter = new ReportFormatter(false);
            out.write(formatter.format(new String[]{"Server", "CPU Busy"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"------", "--------"}));
            out.write(System.getProperty("line.separator"));
            for (Report report : extractions) {
                if (report instanceof PerformanceReport) {
                    String formattedRow = formatter.format(new String[]{report.getAlias() + " (" + report.getServerIP() + ")",
                            formatter.getCPUBusy(report.getIdleTick()) + " %"});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                } else if (report instanceof ExceptionReport) {
                    String formattedRow = formatter.format(new String[]{CONNECTION_EXCEPTION_COMMUNICATE + " " + report.getAlias() +
                            " (" + report.getServerIP() + ")", ""});
                    out.write(formattedRow);
                    out.write(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }*/

    /*static boolean isThreadPoolPresent() {
        for (Report report : extractions) {
            if (!report.getThreadPool().equals("")) {
                return true;
            }
        }
        return false;
    }*/

    private static void print() {
        new ReportManager().print();
    }

    public static String getJarFileName() {
        String path = ASE9App.class.getResource(ASE9App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }
}
