package okon.ASE9;

import okon.ASE9.config.AuthorizationConfigReader;
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
    static final List<Message> messages = new ArrayList();

    public static void main(String[] args) {
        initializeQueue();
        startThreadPool(4);
        print();
    }

    static void initializeQueue() {
        List<Server> servers = ServerConfigReader.readParams((new File("./config/servers.xml")));
        List<Authorization> authorizations = AuthorizationConfigReader.readParams((new File("./config/server-auth.xml")));
        createJobs(servers, authorizations);
    }

    static void createJobs(List<Server> servers, List<Authorization> authorizations) {
        for (Server server : servers) {
            Job job = new Job(server, matchAuthorizationToServer(server, authorizations));
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
            threads[i] = new MessageProducerThread();
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
        String caption = "Serwer Name          Engine Utilization (Tick %)   User Busy   System Busy    I/O Busy        Idle ";
        String lines = "-------------------  -------------------------  ------------  ------------  ----------  ----------";
        System.out.println(caption);
        System.out.println(lines);
        for (Message message : messages) {
            String formattedMessage = new MessageFormatter(message).format();
            System.out.println(formattedMessage);
        }
    }

    static void printToFile() {
        String caption = "Serwer Name          Engine Utilization (Tick %)   User Busy   System Busy    I/O Busy        Idle ";
        String lines = "-------------------  -------------------------  ------------  ------------  ----------  ----------";
        try (Writer out = new FileWriter(new java.io.File(ASE9App.getJarFileName() + ".txt"))) {
            out.write(caption);
            out.write(System.getProperty("line.separator"));
            out.write(lines);
            out.write(System.getProperty("line.separator"));
            for (Message message : messages) {
                String formattedMessage = new MessageFormatter(message).format();
                out.write(formattedMessage);
                out.write(System.getProperty("line.separator"));
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
