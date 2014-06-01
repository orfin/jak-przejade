package jakprzejade.model2;

import jakprzejade.model.GeoPoint;

/**
 * Container representing geographic position
 *
 * @author KonradOliwer
 */
public class Position {

    private static final GeoPoint base = new GeoPoint(51.1098308, 17.0321038);
    private static final double xFactor = 69999.99;
    private static final double yFactor = 11113.29;
    public double x; //[m]
    public double y; //[m]

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Position(GeoPoint p) {
        x = (base.getLongitude() - p.getLongitude()) * xFactor;
        y = (base.getLatitude() - p.getLatitude()) * yFactor;
    }
}
