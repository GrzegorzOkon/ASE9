package okon.ASE9.config;

import okon.ASE9.Setting;
import okon.ASE9.exception.AppException;
import okon.ASE9.Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerConfigReader {
    public static ArrayList<Server> readParams(File file) {
        Element config = parseXml(file);
        ArrayList<Server> result = new ArrayList<>();
        NodeList servers = config.getElementsByTagName("server");
        if (servers != null && servers.getLength() > 0) {
            for (int i = 0; i < servers.getLength(); i++) {
                Node server = servers.item(i);
                if (server.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) server;
                    String databaseAlias = element.getElementsByTagName("db_alias").item(0).getTextContent();
                    String databaseIp = element.getElementsByTagName("db_ip").item(0).getTextContent();
                    Integer databasePort = element.getElementsByTagName("db_port").item(0).getTextContent().equals("") ? null :
                            Integer.valueOf(element.getElementsByTagName("db_port").item(0).getTextContent());
                    List<Setting> settings = new ArrayList<>();
                    for (int j = 0, size = element.getElementsByTagName("db_setting").getLength(); j < size; j++) {
                        String type = element.getElementsByTagName("db_setting").item(j).getAttributes().item(0).getNodeValue();
                        String value = element.getElementsByTagName("db_setting").item(j).getTextContent();
                        settings.add(new Setting(type, value));
                    }
                    String authorizationInterface = element.getElementsByTagName("auth_interface").item(0).getTextContent();
                    result.add(new Server(databaseAlias, databaseIp, databasePort, settings, authorizationInterface));
                }
            }
        }
        return result;
    }

    private static Element parseXml(File file) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            return document.getDocumentElement();
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}