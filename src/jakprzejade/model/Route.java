package jakprzejade.model;

import java.util.LinkedList;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Route {
    private Vehicle vehicle;
    private LinkedList<Stop> stops;

    public Route(Vehicle vehicle) {
        this.vehicle = vehicle;
        stops = new LinkedList<Stop>();
    }
    
    public void addStop(Stop stop) {
        stops.add(stop);
    }
}
