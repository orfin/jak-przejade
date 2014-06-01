package jakprzejade.model2;

import jakprzejade.model.GeoPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Container representing place you can stop (eg. bus stop "Halera")
 *
 * @author KonradOliwer
 */
public class Node {

    private final String id;
    private final Position position;
    private final GeoPoint geoPoint;
    private final String name;
    private List<Path> incoming;
    private List<Path> comingout;

    public Node(String id, String name, GeoPoint geoPoint) {
        this.id = id;
        this.geoPoint = geoPoint;
        position = new Position(geoPoint);
        this.name = name;
        incoming = new ArrayList();
        comingout = new ArrayList();
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public String getName() {
        return name;
    }

    public List<Path> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<Path> incoming) {
        this.incoming = incoming;
    }

    public List<Path> getComingout() {
        return comingout;
    }

    public void setComingout(List<Path> comingout) {
        this.comingout = comingout;
    }
}
