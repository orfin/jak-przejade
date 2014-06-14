package jakprzejade.routefinder;

import jakprzejade.dto.FindRouteRequest;
import jakprzejade.dto.FindRouteResponse;
import jakprzejade.dto.Node;
import jakprzejade.dto.Route;
import jakprzejade.model.GeoPoint;

/**
 * @author Antoni Orfin <orfin@poczta.fm>
 */
public class TestRouteFinder {

    public FindRouteResponse findRoute(FindRouteRequest request) {
        GeoPoint from = request.from;
        GeoPoint to = request.to;

        FindRouteResponse response = new FindRouteResponse();
        Route r1 = new Route();
        r1.totalTime = 50;
        r1.paths.add(getSamplePath(from, true, null));
        r1.paths.add(getSamplePath(null, false, null));
        r1.paths.add(getSamplePath(to, false, null));
        response.routes.add(r1);

        Route r2 = new Route();
        r2.paths.add(getSamplePath(from, true, null));
        r2.paths.add(getSamplePath(to, false, null));
        response.routes.add(r2);

        Route r3 = new Route();
        r3.paths.add(getSamplePath(from, true, to));
        response.routes.add(r3);

        return response;
    }

    public jakprzejade.dto.Path getSamplePath(GeoPoint point, boolean start, GeoPoint to) {
        jakprzejade.dto.Path p1 = new jakprzejade.dto.Path();

        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        n1.name = "Krzemieniecka";
        n1.time = "14:52";

        if (point != null && start) {
            n1.lat = point.getLatitude();
            n1.lon = point.getLongitude();
        } else {
            n1.lat = 51.093388685607174 - Math.random() / 100;
            n1.lon = 16.980743408203125 - Math.random() / 100;
        }

        n2.name = "Końcowa";
        n2.time = "14:55";
        n2.lat = 51.104168670625924 - Math.random() / 100;
        n2.lon = 17.02228546142578 - Math.random() / 100;

        n3.name = "FAT";
        n3.time = "15:05";

        if (point != null && !start) {
            n3.lat = point.getLatitude();
            n3.lon = point.getLongitude();
        } else {
            n3.lat = 51.10643214814799 - Math.random() / 100;
            n3.lon = 17.048892974853516 - Math.random() / 100;

            if (to != null) {
                n3.lat = to.getLatitude();
                n3.lon = to.getLongitude();
            }
        }

        p1.vehicleName = "" + (int) (Math.random() * 1000);
        p1.vehicleType = "bus";

        p1.nodes.add(n1);
        p1.nodes.add(n2);
        p1.nodes.add(n3);

        return p1;
    }
}
