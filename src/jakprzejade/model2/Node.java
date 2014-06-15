package jakprzejade.model2;

import jakprzejade.model.GeoPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Container representing place you can stop (ex. bus stop "Halera")
 *
 * @author KonradOliwer
 */
public class Node {

    private final String id;
    private final Position position;
    private final GeoPoint geoPoint;
    private final String name;
    private final List<Path> paths;

    public Node(String id, String name, GeoPoint geoPoint) {
        this.id = id;
        this.geoPoint = geoPoint;
        position = new Position(geoPoint);
        this.name = name;
        paths = new ArrayList();
    }

    public void addPaths(Path path) {
        paths.add(path);
    }

    public List<Path> getPaths() {
        return paths;
    }

    public String getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setGeoPoint(double longitude, double latitude) {
        this.geoPoint.setLatitude(latitude);
        this.geoPoint.setLongitude(longitude);
    }
    
    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public String getName() {
        return name;
    }
}
