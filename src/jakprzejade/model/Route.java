package jakprzejade.model;

import java.util.LinkedList;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Route {
    public Vehicle vehicle;
    public LinkedList<Stop> stops;

    public Route(Vehicle vehicle) {
        this.vehicle = vehicle;
        stops = new LinkedList<Stop>();
    }
    
    public void addStop(Stop stop) {
        stops.add(stop);
    }
}
