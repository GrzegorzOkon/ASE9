package okon.ASE9;

import okon.ASE9.security.HexDecryptor;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class WorkingObjects {
    public static final Queue<Job> jobs = new LinkedList<>();

    public static void setJobs(Properties parameters) {
        if (parameters.containsKey("Server") && !parameters.getProperty("Server").equals("")) {
            String[] servers = parameters.getProperty("Server").split(";");
            for (int iter = 0; iter < servers.length; iter++) {
                String ip = substringIp(servers[iter]);
                int port = Integer.valueOf(substringPort(servers[iter]));
                String login = substringLogin(servers[iter]);
                String password = unmask(substringPasssword(servers[iter]));
                String alias = substringAlias(servers[iter]);
                jobs.add(new Job(ip, port, login, password, alias));
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

    private static String substringAlias(String server) {
        return server.substring(server.indexOf("{") + 1, server.indexOf("}"));
    }

    private static String unmask(String password) {
        String result = null;
        if (WorkingEnvironment.getMaskType().equals(("unmasked"))) {
            result = password;
        } else if (WorkingEnvironment.getMaskType().equals("hex")){
            result = HexDecryptor.convert(password);
        }
        return result;
    }
}
