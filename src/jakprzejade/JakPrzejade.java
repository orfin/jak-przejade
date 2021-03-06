package jakprzejade;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;
import jakprzejade.importer.Importer;
import jakprzejade.model.*;
import jakprzejade.model2.GlobalKnowledge;
import jakprzejade.routefinder.RouteFinder;
import jakprzejade.routefinder.dj.Dj;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
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
        GlobalKnowledge.init();

        Logger.getAnonymousLogger().log(Level.INFO, "Nodes size: " + GlobalKnowledge.nodesMap.size());
        Logger.getAnonymousLogger().log(Level.INFO, GlobalKnowledge.nodesMap.toString());

        FindRouteRequest request = new FindRouteRequest();
        request.from = new GeoPoint(16.99930429458618, 51.08522791325631);
        request.to = new GeoPoint(17.061874866485596, 51.10746280207861);
        request.date = Calendar.getInstance();
        request.date.set(request.date.get(Calendar.YEAR),
                request.date.get(Calendar.MONTH),
                request.date.get(Calendar.DAY_OF_MONTH),
                11,
                15);
        
        long timeStart = System.currentTimeMillis();
        
        RouteFinder routeFinder = new Dj();
        FindRouteResponse response = routeFinder.findRoute(request);
        
        Importer.getLogger().log(Level.INFO, "Route found in {0} ms", "" + Importer.getTimeTotal(timeStart));
        
        System.out.println(response.toString());
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
