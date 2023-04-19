package route;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class RouteBuilder {
    public static StationsMatrix loadStations() {
        StationsMatrix stations;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src/stations.xml"));
            NodeList nl = doc.getElementsByTagName("station");
            stations = new StationsMatrix(nl.getLength() / 10, 10);

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    String city = el.getElementsByTagName("city").item(0).getTextContent();
                    String x = el.getElementsByTagName("x").item(0).getTextContent();
                    String y = el.getElementsByTagName("y").item(0).getTextContent();

                    Station st = new Station(city, Integer.parseInt(x), Integer.parseInt(y));
                    stations.addStation(st);
                }
            }

            stations.sort();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }

        return stations;
    }

}
