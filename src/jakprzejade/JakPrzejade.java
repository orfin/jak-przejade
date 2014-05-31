package jakprzejade;

import jakprzejade.importer.Repository;
import jakprzejade.importer.zdik.ZdikImporter;
import jakprzejade.model.*;
import java.util.Arrays;
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
        ScheduleTime st = new ScheduleTime(23);
        Schedule sd = new Schedule(Schedule.ScheduleType.SUNDAY);
        
        sd.addTime(st);
        System.out.println(sd.getTimes().toString());
        
        RepositoryHandler.initRepository();
        
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
