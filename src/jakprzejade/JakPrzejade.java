package jakprzejade;

import jakprzejade.importer.Repository;
import jakprzejade.importer.zdik.ZdikImporter;
import jakprzejade.model.*;
import jakprzejade.model2.DayType;
import jakprzejade.model2.GlobalKnowledge;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class JakPrzejade {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        GlobalKnowledge.initNodes();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Nodes size: " + GlobalKnowledge.nodesMap.size());
        Logger.getAnonymousLogger().log(Level.INFO, GlobalKnowledge.nodesMap.toString());
        
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
