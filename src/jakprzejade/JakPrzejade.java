package jakprzejade;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;
import jakprzejade.dynamicprogramming.Algorithm;
import jakprzejade.importer.Repository;
import jakprzejade.importer.zdik.ZdikImporter;
import jakprzejade.model.*;
import jakprzejade.model2.DayType;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.routefinder.RouteFinder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class JakPrzejade {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        GlobalKnowledge.initNodes();

        Logger.getAnonymousLogger().log(Level.INFO, "Nodes size: " + GlobalKnowledge.nodesMap.size());
        Logger.getAnonymousLogger().log(Level.INFO, GlobalKnowledge.nodesMap.toString());

        FindRouteRequest request = new FindRouteRequest();
        request.from = new GeoPoint(17.000162601470947, 51.08527508948859); // 
        request.to = new GeoPoint(17.03052520751953, 51.10279436233925);
        request.date = Calendar.getInstance();
        request.date.set(request.date.get(Calendar.YEAR), 
                request.date.get(Calendar.MONTH), 
                request.date.get(Calendar.DAY_OF_MONTH),
                11, 
                15);
        RouteFinder routeFinder = new Algorithm();
        FindRouteResponse response = routeFinder.findRoute(request);
//        for (BusStop busStop : repo.busStops.values()) {
//            System.out.println(busStop);
//        }
//        
//        System.out.println("vehicles");
//         
//        for (Vehicle vehicle : repo.vehicles.values()) {
//            System.out.println(vehicle);
//        }
    }
}
