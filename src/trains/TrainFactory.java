package trains;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import route.Route;
import route.StationsMatrix;
import route.exceptions.StationNotFound;
import trains.exceptions.InvalidParams;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TrainFactory {
    public static ArrayList<Locomotive> loadTrains(StationsMatrix stations) {
        ArrayList<Locomotive> locos = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("src/trains.xml"));
            NodeList nl = doc.getElementsByTagName("train");

            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    String name = el.getElementsByTagName("name").item(0).getTextContent();
                    String from = el.getElementsByTagName("from").item(0).getTextContent();
                    String to = el.getElementsByTagName("to").item(0).getTextContent();
                    String parent = el.getElementsByTagName("parent").item(0).getTextContent();
                    int maxCars = Integer.parseInt(el.getElementsByTagName("maxcars").item(0).getTextContent());
                    int maxElectric = Integer.parseInt(el.getElementsByTagName("maxelectric").item(0).getTextContent());
                    int maxWeight = Integer.parseInt(el.getElementsByTagName("weight").item(0).getTextContent());

                    Route route = stations.getPath(from, to);

                    Locomotive loco = new Locomotive(name, route, route.getReturnRoute(), parent, maxCars, maxElectric, maxWeight);

                    while (true) {
                        try {
                            loco.addCar(getRandomCar());
                        } catch (Exception e) {
                            break;
                        }
                    }

                    locos.add(loco);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return locos;
    }

    public static Locomotive parseParams(StationsMatrix stations, String params) throws InvalidParams, StationNotFound {
        String[] p = params.split(",");

        if (p.length != 7) {
            throw new InvalidParams();
        }

        String name = p[0];
        String from = p[1];
        String to = p[2];
        String parent = p[3];
        int maxCars, maxElectric, maxWeight;

        if (to.equals(from)) throw new InvalidParams();

        try {
            maxCars = Integer.parseInt(p[4]);
            maxElectric = Integer.parseInt(p[5]);
            maxWeight = Integer.parseInt(p[6]);
        } catch (Exception e) {
            throw new InvalidParams();
        }

        Route route = stations.getPath(from, to);
        return new Locomotive(name, route, route.getReturnRoute(), parent, maxCars, maxElectric, maxWeight);
    }

    public static TrainCar getCar(String type, Scanner scanner) throws Exception {
        return switch (type) {
            case "passenger" -> PassengerCar.createFromScanner(scanner);
            case "mail" -> MailCar.createFromScanner(scanner);
            case "baggage" -> BaggageCar.createFromScanner(scanner);
            case "restaurant" -> RestaurantCar.createFromScanner(scanner);
            case "freight" -> new FreightCar();
            case "heavy_freight" -> new HeavyFreightCar();
            case "liquid" -> LiquidCargoCar.createFromScanner(scanner);
            case "cooling" -> CoolingCar.createFromScanner(scanner);
            case "gas" -> GasCar.createFromScanner(scanner);
            case "explosives" -> ExplosivesCar.createFromScanner(scanner);
            case "toxic" -> ToxicCar.createFromScanner(scanner);
            case "liq_toxic" -> LiquidToxicCar.createFromScanner(scanner);
            default -> throw new Exception("Car not found");
        };
    }

    public static TrainCar getCar(String type) throws Exception {
        return switch (type) {
            case "passenger" -> new PassengerCar();
            case "mail" -> new MailCar();
            case "baggage" -> new BaggageCar();
            case "restaurant" -> new RestaurantCar();
            case "freight" -> new FreightCar();
            case "heavy_freight" -> new HeavyFreightCar();
            case "liquid" -> new LiquidCargoCar(25);
            case "cooling" -> new CoolingCar("Meat");
            case "gas" -> new GasCar("Nitrogen");
            case "explosives" -> new ExplosivesCar("Dynamite");
            case "toxic" -> new ToxicCar(Toxicity.LOW);
            case "liq_toxic" -> new LiquidToxicCar(25, Toxicity.LOW);
            default -> throw new Exception("Car not found");
        };
    }

    public static TrainCar getRandomCar() throws Exception {
        String[] types = {"passenger", "mail", "baggage", "restaurant", "freight", "heavy_freight", "liquid", "cooling", "gas", "explosives", "toxic", "liq_toxic"};
        Random rand = new Random();

        return getCar(types[rand.nextInt(types.length)]);
    }
}
