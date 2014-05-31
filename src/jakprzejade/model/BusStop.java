package jakprzejade.model;

import java.util.HashMap;

/**
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class BusStop {

    private String id;
    private String name;
    private String streetName;
    private GeoPoint location;
    
    // String is a vehicle identifier
    private HashMap<String, Stop> stops;

    public BusStop(String id, String name, String streetName) {
        this.id = id;
        this.name = name;
        this.streetName = streetName;
        
        stops = new HashMap<String, Stop>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreetName() {
        return streetName;
    }

    public HashMap<String, Stop> getStops() {
        return stops;
    }

    public Stop getStop(String id) {
        return stops.get(id);
    }
    
    public void addStop(Stop stop) {
        stops.put(stop.getVehicle().getName(), stop);
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setLocation(double longitude, double latitude) {
        this.location = new GeoPoint(longitude, latitude);
    }

    @Override
    public String toString() {
        return "BusStop{" + "id=" + id + ", name=" + name + ", streetName=" + streetName + ", location=" + location + ", stops=" + stops + '}';
    }
    
}
