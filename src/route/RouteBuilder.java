package route;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import trains.exceptions.InvalidParams;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class RouteBuilder {
    public static StationsMatrix loadStations() {
        StationsMatrix stations;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src/stations.xml"));
            NodeList nl = doc.getElementsByTagName("station");
            ArrayList<Station> sts = new ArrayList<>();

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    String city = el.getElementsByTagName("city").item(0).getTextContent();
                    String x = el.getElementsByTagName("x").item(0).getTextContent();
                    String y = el.getElementsByTagName("y").item(0).getTextContent();

                    Station st = new Station(city, Integer.parseInt(x), Integer.parseInt(y));
                    sts.add(st);
                }
            }

            stations = new StationsMatrix(sts);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }

        return stations;
    }

    public static Station parseParams(String params, StationsMatrix stations) throws Exception {
        String[] p = params.split(",");

        if (p.length != 3) {
            throw new InvalidParams();
        }

        String name = p[0];

        if (name.equals("")) {
            throw new InvalidParams();
        }

        int x, y;

        try {
            x = Integer.parseInt(p[1]);
            y = Integer.parseInt(p[2]);
        } catch (Exception e) {
            throw new InvalidParams();
        }

        if (stations.stationExists(x, y)) {
            throw new Exception("There is already a station under given coordinates");
        }

        if (stations.stationExists(name)) {
            throw new Exception("There is already a station with a given name");
        }

        if ((x < 0 || x > 100)) {
            throw new Exception("Stations x should be between 0 and 100");
        }

        if ((y < 0 || y > 100)) {
            throw new Exception("Stations y should be between 0 and 100");
        }

        return new Station(name, x, y);
    }

}
