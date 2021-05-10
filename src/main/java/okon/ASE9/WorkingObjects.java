package okon.ASE9;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class WorkingObjects {
    public static final Queue<Job> jobs = new LinkedList<>();

    public static void setJobs(Properties parameters) {
        if (parameters.contains("Server") && !parameters.getProperty("Server").equals("")) {
            for (String server : parameters.getProperty("Server").split(";")) {
                String ip = substringIp(server);
                int port = Integer.valueOf(substringPort(server));
                String login = substringLogin(server);
                String password = substringPasssword(server);
                jobs.add(new Job(ip, port, login, password));
            }
        }
    }

    private static String substringIp(String server) {
        return server.substring(0, server.indexOf(':'));
    }

    private static String substringPort(String server) {
        return server.substring(server.indexOf(":") + 1, server.indexOf("["));
    }

    private static String substringLogin(String server) {
        return server.substring(server.indexOf("[") + 1, server.indexOf(","));
    }

    private static String substringPasssword(String server) {
        return server.substring(server.indexOf(",") + 1, server.indexOf("]"));
    }
}
