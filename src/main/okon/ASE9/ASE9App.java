package okon.ASE9;

import org.w3c.dom.Element;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ASE9App {
    static final ConnectionFactory connectionFactory = new ConnectionFactory();
    static Queue<DataSource> dataSourceQueue;
    static final List<Message> messageList = new ArrayList();

    private final DataSourceBuilder dataSourceBuilder = new DataSourceBuilder();

    public static void main (String args[]) {
        ASE9App ase9_app = new ASE9App();

        dataSourceQueue = ase9_app.loadConfiguration("./settings/config.xml");

        ase9_app.startThreadPool(2);

        ase9_app.save("ASE9.txt", messageList);
    }

    private Queue<DataSource> loadConfiguration(String pathname) {
        ConfigurationParser parser = new ConfigurationParser();
        Element root = parser.parseXml(new File(pathname));

        return dataSourceBuilder.build(root);
    }

    private void startThreadPool(int threadSum) {
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

    public void save(String fileName, List<Message> content) {
        String caption = "Serwer Name          Engine Utilization (Tick %)   User Busy   System Busy    I/O Busy        Idle ";
        String lines = "-------------------  -------------------------  ------------  ------------  ----------  ----------";

        try (FileOutputStream out = new FileOutputStream(new java.io.File(fileName))) {
            out.write(caption.getBytes());
            out.write(System.getProperty("line.separator").getBytes());
            out.write(lines.getBytes());
            out.write(System.getProperty("line.separator").getBytes());

            for(Message message : content) {
                String formattedMessage = new MessageFormatter(message).format();

                out.write(formattedMessage.getBytes());
                out.write(System.getProperty("line.separator").getBytes());
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}
