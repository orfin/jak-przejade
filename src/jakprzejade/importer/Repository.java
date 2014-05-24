package jakprzejade.importer;

import jakprzejade.model.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents data from imported files.
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class Repository {

    public HashMap<String, Vehicle> vehicles;
    public HashMap<String, BusStop> busStops;
    public ArrayList<Route> routes;

    public Repository() {
        vehicles = new HashMap<String, Vehicle>();
        busStops = new HashMap<String, BusStop>();
        routes = new ArrayList<Route>();
    }

    
    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getName(), vehicle);
    }

    public void addBusStop(BusStop busStop) {
        busStops.put(busStop.getId(), busStop);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}
