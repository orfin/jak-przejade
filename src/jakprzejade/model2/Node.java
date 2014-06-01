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
    
    public final Position position;
    public final GeoPoint geoPoint;
    public final String name;
    public final List<Path> paths;

    private Node(String name, GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
        position = new Position(geoPoint);
        this.name = name;
        paths = new ArrayList();
    }

}
